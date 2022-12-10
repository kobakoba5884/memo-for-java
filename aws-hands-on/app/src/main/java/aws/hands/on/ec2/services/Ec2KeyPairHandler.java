package aws.hands.on.ec2.services;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.ec2.model.CreateKeyPairRequest;
import software.amazon.awssdk.services.ec2.model.CreateKeyPairResponse;
import software.amazon.awssdk.services.ec2.model.DeleteKeyPairRequest;
import software.amazon.awssdk.services.ec2.model.DescribeKeyPairsRequest;
import software.amazon.awssdk.services.ec2.model.DescribeKeyPairsResponse;
import software.amazon.awssdk.services.ec2.model.Ec2Exception;
import software.amazon.awssdk.services.ec2.model.KeyPairInfo;

import static aws.hands.on.ec2.configs.Ec2Config.FOLDER_PATH;

public class Ec2KeyPairHandler{

    private Ec2KeyPairHandler(){

    }

    // create key pair
    public static void createEC2KeyPair(Ec2Client ec2Client, String keyPairName){
        try{
            CreateKeyPairRequest createKeyPairRequest = CreateKeyPairRequest.builder()
                .keyName(keyPairName)
                .build();

            CreateKeyPairResponse createKeyPairResponse = ec2Client.createKeyPair(createKeyPairRequest);
            System.out.printf("Successfully created key pair named %s\n", keyPairName);

            String privateKey = createKeyPairResponse.keyMaterial();

            createPemFile(keyPairName, privateKey);

        }catch(Ec2Exception e){
            System.err.println(e.awsErrorDetails().errorMessage());
        }
    }

    // create pem file of key pair (private key) 
    private static void createPemFile(String keyPairName, String privateKey){
        String fileName = String.format("%s.pem", keyPairName);
        Path outputPath = FOLDER_PATH.resolve(fileName);
        File outputFile = outputPath.toFile();

        try{
            if(FOLDER_PATH.toFile().mkdir() | outputFile.createNewFile()){
                System.out.println(outputPath);
                try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outputFile));){
                    bufferedWriter.write(privateKey);
                    System.out.printf("copy complete! to %s\n", outputPath.toString());
                }
            }else{
                System.out.printf("%s is already existed!!\n", outputFile.getName());
            }
        }catch(IOException e){
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    // delete pem file
    private static void deletePemFile(String keyPairName){
        String fileName = String.format("%s.pem", keyPairName);
        Path targetPath = FOLDER_PATH.resolve(fileName);

        if(targetPath.toFile().delete()){
            System.out.printf("Deleted the file: %s\n", fileName);
        }else{
            System.out.println("Failed to delete the file.");
        }

        if(folderIsEmpty()) {
            FOLDER_PATH.toFile().delete();
        }
    }

    // discriminate whether folder is empty
    private static boolean folderIsEmpty(){
        if (Files.isDirectory(FOLDER_PATH)) {
            try (Stream<Path> entries = Files.list(FOLDER_PATH)) {
                return !entries.findFirst().isPresent();
            }catch(IOException e){
                System.err.println(e.getMessage());
            }
        }
            
        return false;
    }

    // get key pair list
    public static Optional<List<KeyPairInfo>> getEC2KeyPairs(Ec2Client ec2Client){
        
        try{
            DescribeKeyPairsResponse response = ec2Client.describeKeyPairs();
            List<KeyPairInfo> keyPairInfos = response.keyPairs();

            if(keyPairInfos.size() == 0) return Optional.empty();

            keyPairInfos.forEach(keyPair -> {
                System.out.printf("Found key pair with name %s and fingerprint %s\n", keyPair.keyName(), keyPair.keyFingerprint());
            });

            return Optional.of(keyPairInfos);
        }catch(Ec2Exception e){
            System.err.println(e.awsErrorDetails().errorMessage());
            return Optional.empty();
        }
    }

    // get key pair
    public static Optional<KeyPairInfo> getEC2KeyByKeyName(Ec2Client ec2Client, String keyPairName){
        try{
            DescribeKeyPairsRequest describeKeyPairsRequest = DescribeKeyPairsRequest.builder()
                .keyNames(keyPairName)
                .build();

            DescribeKeyPairsResponse describeKeyPairsResponse = ec2Client.describeKeyPairs(describeKeyPairsRequest);

            KeyPairInfo keyPairInfo = describeKeyPairsResponse.keyPairs().get(0);

            System.out.printf("found keyPair!! (%s)\n", keyPairName);

            return Optional.of(keyPairInfo);
        }catch(Ec2Exception e){
            System.err.println(e.awsErrorDetails().errorMessage());
            return Optional.empty();
        }
    }

    // delete key pair
    public static void deleteKeyPair(Ec2Client ec2Client, String keyPairName) {
        try{
            DeleteKeyPairRequest deleteKeyPairRequest = DeleteKeyPairRequest.builder()
                .keyName(keyPairName)
                .build();
            
            ec2Client.deleteKeyPair(deleteKeyPairRequest);
            System.out.printf("Successfully deleted key pair named %s\n", keyPairName);

            deletePemFile(keyPairName);
        }catch (Ec2Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());
        }
    }

    // all delete key pair
    public static void allDeleteKeys(Ec2Client ec2Client){
        Optional<List<KeyPairInfo>> keyPairInfosOptional = getEC2KeyPairs(ec2Client);

        if(keyPairInfosOptional.isEmpty()){
            System.out.println("key pairs's list is empty!!");
            return;
        }

        List<KeyPairInfo> keyPairInfos = keyPairInfosOptional.get();

        keyPairInfos.stream().forEach(keyPair -> deleteKeyPair(ec2Client, keyPair.keyName()));

        System.out.printf("successfully delete all of key pairs!! (total: %d)\n", keyPairInfos.size());
    }
}
