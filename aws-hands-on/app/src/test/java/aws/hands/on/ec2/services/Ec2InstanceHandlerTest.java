package aws.hands.on.ec2.services;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import aws.hands.on.ec2.models.Ec2InstanceDTO;
import software.amazon.awssdk.services.ec2.model.Instance;

import static aws.hands.on.ec2.services.Ec2InstanceHandler.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Objects;
import java.util.Optional;

@TestInstance(TestInstance.Lifecycle.PER_CLASS) // https://www.baeldung.com/java-beforeall-afterall-non-static
public class Ec2InstanceHandlerTest extends Ec2Test{
    private String instanceId;

    @BeforeAll
    void setup(){
        System.out.println("setup!!");
        // instanceId = createEc2Instance(ec2Client);
    }

    @BeforeEach
    void init() throws InterruptedException{
        if(Objects.isNull(instanceId)){
            System.out.println("start test!");
            return;
        }

        System.out.println("next to test based in " + instanceId);
        Thread.sleep(3000);
    }

    @Test
    void testCreateEc2Instance(){
        Ec2InstanceDTO ec2InstanceDTO = new Ec2InstanceDTO();
        createEc2Instance(ec2Client, ec2InstanceDTO);
    }

    @Test
    void testGetInstanceByInstanceId() {
        Optional<Instance> instance = getInstanceByInstanceId(ec2Client, instanceId);

        instance.ifPresent(i -> {
            assertEquals(instanceId, i.instanceId());
        });
    }

    @Test
    void testGetInstancesList() {
    }

    @Test
    void testStopEC2Instance() throws InterruptedException {
        stopEC2Instance(ec2Client, instanceId);
    }

    @Test
    void testStartEC2Instance() {
        System.out.println("before start handling");
        startEC2Instance(ec2Client, instanceId);
    }

    @Test
    void testTerminateEC2Instance() {
        System.out.println("before terminate handling");
        terminateEC2Instance(ec2Client, "i-0f790a4c0f52e61d1");
    }
}
