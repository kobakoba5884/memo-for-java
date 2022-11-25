package aws.practice.ec2;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.services.ec2.Ec2Client;

import static aws.practice.credential.CredentioalsInfo.MY_REGION;
import static aws.practice.ec2.Ec2KeyPairHandler.*;


public class Ec2Handler {
    public static void main(String[] args) {
        Ec2Client ec2Client = Ec2Client.builder()
            .region(MY_REGION)
            .credentialsProvider(ProfileCredentialsProvider.create())
            .build();

            // createEC2KeyPair(ec2Client, "test4");
            // getEC2KeyByKeyName(ec2Client, "test");
            // deleteKeys(ec2Client, "test");
            // deletePemFile("test2");
            allDeleteKeys(ec2Client);
        ec2Client.close();
    }
}
