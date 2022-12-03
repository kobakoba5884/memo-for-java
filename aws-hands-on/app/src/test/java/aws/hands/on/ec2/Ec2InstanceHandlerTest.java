package aws.hands.on.ec2;

import org.junit.jupiter.api.Test;

import aws.hands.on.AppTest;

import software.amazon.awssdk.services.ec2.model.Instance;

import static aws.hands.on.ec2.Ec2InstanceHandler.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

public class Ec2InstanceHandlerTest extends AppTest{
    @Test
    void testCreateEc2Instance() throws InterruptedException {
        @SuppressWarnings("unused")
        String instanceId = createEc2Instance(ec2Client);
    }

    @Test
    void testGetInstanceByInstanceId() {
        String instanceId = createEc2Instance(ec2Client);

        Optional<Instance> instance = getInstanceByInstanceId(ec2Client, instanceId);

        instance.ifPresent(i -> {
            assertEquals(instanceId, i.instanceId());
        });

        terminateEC2Instance(ec2Client, instanceId);
    }

    @Test
    void testGetInstancesList() {
    }

    @Test
    void testTerminateEC2Instance() {
    }
}
