package aws.practice.ec2;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.ec2.model.CreateKeyPairRequest;
import software.amazon.awssdk.services.ec2.model.DeleteKeyPairRequest;
import software.amazon.awssdk.services.ec2.model.DescribeKeyPairsResponse;
import software.amazon.awssdk.services.ec2.model.Ec2Exception;
import software.amazon.awssdk.services.ec2.model.KeyPairInfo;

public class Ec2KeyPairHandler{

    private Ec2KeyPairHandler(){

    }
    // create key pair
    public static void createEC2KeyPair(Ec2Client ec2Client, String keyPair){
        try{
            CreateKeyPairRequest createKeyPairRequest = CreateKeyPairRequest.builder()
                .keyName(keyPair)
                .build();

            ec2Client.createKeyPair(createKeyPairRequest);
            System.out.printf("Successfully created key pair named %s\n", keyPair);
        }catch(Ec2Exception e){
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
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

    // delete key pair
    public static void deleteKeys(Ec2Client ec2Client, String keyPair) {
        try{
            DeleteKeyPairRequest deleteKeyPairRequest = DeleteKeyPairRequest.builder()
                .keyName(keyPair)
                .build();
            
            ec2Client.deleteKeyPair(deleteKeyPairRequest);
            System.out.printf("Successfully deleted key pair named %s\n", keyPair);
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
        System.out.printf("successfully delete all of key pairs!! (total: %d)", keyPairInfos.size());
    }
}
