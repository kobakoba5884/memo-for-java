package aws.hands.on.ec2.services;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import aws.hands.on.ec2.models.Ec2InstanceDTO;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.services.ec2.Ec2Client;

import static aws.hands.on.ec2.services.Ec2InstanceHandler.*;

import static aws.hands.on.credential.CredentialsInfo.MY_REGION;

@TestInstance(TestInstance.Lifecycle.PER_CLASS) 
public class Ec2Test {
    protected Ec2Client ec2Client;
    protected String targetInstanceId;

    @BeforeAll
    void setupAppTest(){
        this.ec2Client = Ec2Client.builder()
            .region(MY_REGION)
            .credentialsProvider(ProfileCredentialsProvider.create())
            .build();

        Ec2InstanceDTO ec2InstanceDTO = new Ec2InstanceDTO();
        
        this.targetInstanceId = createEc2Instance(ec2Client, ec2InstanceDTO);
    }

    @AfterAll
    void teardownAppTest(){
        terminateEC2Instance(ec2Client, targetInstanceId);
        ec2Client.close();
    }

    @Test
    void test(){

    }
}
