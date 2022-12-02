package aws.hands.on.ec2;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.ec2.model.Instance;

import static aws.hands.on.credential.CredentioalsInfo.MY_REGION;
import static aws.hands.on.ec2.Ec2InstanceHandler.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Optional;

public class Ec2InstanceHandlerTest {
    private Ec2Client ec2Client;

    @BeforeEach
    void init(){
        this.ec2Client = Ec2Client.builder()
            .region(MY_REGION)
            .credentialsProvider(ProfileCredentialsProvider.create())
            .build();
    }

    @AfterEach
    void teardown(){
        ec2Client.close();
    }

    @Test
    void testCreateEc2Instance() throws InterruptedException {
        String instanceId = createEc2Instance(ec2Client);

        @SuppressWarnings("unused")
        Optional<List<Instance>> instances = getInstancesList(ec2Client);
        Optional<Instance> instance = getInstanceByInstanceId(ec2Client, instanceId);

        instance.ifPresent(i -> {
            assertEquals(instanceId, i.instanceId());
        });

        terminateEC2Instance(ec2Client, instanceId);
    }

    @Test
    void testGetInstanceByInstanceId() {
    }

    @Test
    void testGetInstancesList() {
    }

    @Test
    void testTerminateEC2Instance() {
    }
}
