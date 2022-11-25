package aws.practice.ec2;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
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

import static aws.practice.credential.CredentioalsInfo.EC2_FOLDER_PATH;

public class Ec2KeyPairHandler{
    private static Path baseFolderPath = Paths.get(System.getProperty("user.home"));
    private static Path folderPath = baseFolderPath.resolve(EC2_FOLDER_PATH);

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
            System.exit(1);
        }
    }

    // create pem file of key pair (private key) 
    private static void createPemFile(String keyPairName, String privateKey){
        String fileName = String.format("%s.pem", keyPairName);
        Path outputPath = folderPath.resolve(fileName);

        File outputFile = outputPath.toFile();

        try{
            if(folderPath.toFile().mkdir() | outputFile.createNewFile()){
                System.out.println(outputPath);
                try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outputFile));){
                    bufferedWriter.write(privateKey);
                    System.out.printf("copy complete! to %s\n", outputPath.toString());
                }
            }else{
                System.out.printf("%s is already existed!!\n", fileName);
            }
        }catch(IOException e){
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    // delete pem file
    private static void deletePemFile(String keyPairName){
        String fileName = String.format("%s.pem", keyPairName);
        Path targetPath = folderPath.resolve(fileName);

        if(targetPath.toFile().delete()){
            System.out.printf("Deleted the file: %s\n", fileName);
        }else{
            System.out.println("Failed to delete the file.");
        }

        if(folderIsEmpty()) {
            folderPath.toFile().delete();
        }
    }

    // discriminate whether folder is empty
    private static boolean folderIsEmpty(){
        if (Files.isDirectory(folderPath)) {
            try (Stream<Path> entries = Files.list(folderPath)) {
                return !entries.findFirst().isPresent();
            }catch(IOException e){
                System.err.println(e.getMessage());
            }
        }
            
        return false;
    }

    // get key pair list
    public static Optional<List<KeyPairInfo>> getEC2Keys(Ec2Client ec2Client){
        List<KeyPairInfo> keyPairInfos = new ArrayList<>();
        try{
            DescribeKeyPairsResponse response = ec2Client.describeKeyPairs();
            keyPairInfos = response.keyPairs();

            if(keyPairInfos.size() == 0) return Optional.empty();

            keyPairInfos.forEach(keyPair -> {
                System.out.printf("Found key pair with name %s and fingerprint %s\n", keyPair.keyName(), keyPair.keyFingerprint());
            });
        }catch(Ec2Exception e){
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
        return Optional.of(keyPairInfos);
    }

    // get key pair
    public static Optional<KeyPairInfo> getEC2KeyByKeyName(Ec2Client ec2Client, String keyPairName){
        KeyPairInfo keyPairInfo = null;
        try{
            DescribeKeyPairsRequest describeKeyPairsRequest = DescribeKeyPairsRequest.builder()
                .keyNames(keyPairName)
                .build();

            DescribeKeyPairsResponse describeKeyPairsResponse = ec2Client.describeKeyPairs(describeKeyPairsRequest);

            keyPairInfo = describeKeyPairsResponse.keyPairs().get(0);
            System.out.printf("found keyPair!! (%s)\n", keyPairName);
        }catch(Ec2Exception e){
            String message = e.awsErrorDetails().errorMessage();

            System.err.println(message);
            if(message.equals(String.format("The key pair '%s' does not exist", keyPairName))) return Optional.empty();

            System.exit(1);
        }
        return Optional.of(keyPairInfo);
    }

    // delete key pair
    public static void deleteKeys(Ec2Client ec2Client, String keyPairName) {
        try{
            DeleteKeyPairRequest deleteKeyPairRequest = DeleteKeyPairRequest.builder()
                .keyName(keyPairName)
                .build();
            
            ec2Client.deleteKeyPair(deleteKeyPairRequest);
            System.out.printf("Successfully deleted key pair named %s\n", keyPairName);

            deletePemFile(keyPairName);
        }catch (Ec2Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
    }

    // all delete key pair
    public static void allDeleteKeys(Ec2Client ec2Client){
        Optional<List<KeyPairInfo>> keyPairInfosOptional = getEC2Keys(ec2Client);

        if(keyPairInfosOptional.isEmpty()){
            System.out.println("key pairs's list is empty!!");
            return;
        }
        List<KeyPairInfo> keyPairInfos = keyPairInfosOptional.get();

        keyPairInfos.stream().forEach(keyPair -> deleteKeys(ec2Client, keyPair.keyName()));
        System.out.printf("successfully delete all of key pairs!! (total: %d)\n", keyPairInfos.size());
    }
}
