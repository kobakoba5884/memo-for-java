package aws.hands.on.ec2;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.services.ec2.Ec2Client;

import static aws.hands.on.credential.CredentialsInfo.MY_REGION;
import static aws.hands.on.credential.CredentialsInfo.DEFAULT_VPC_ID;
import static aws.hands.on.ec2.services.Ec2SecurityGroupHandler.*;


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
            // allDeleteKeys(ec2Client);
            // getInstanceByInstanceId(ec2Client, "instanceId");
            // getInstancesList(ec2Client);
            createEC2SecurityGroup(ec2Client, "groupName3", "groupDesc", DEFAULT_VPC_ID);
            // getInstanceByInstanceId(ec2Client, "i-1234567890abcdef0");
        ec2Client.close();
    }
}
