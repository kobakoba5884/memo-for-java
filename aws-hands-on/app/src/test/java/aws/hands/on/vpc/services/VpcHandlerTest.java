package aws.hands.on.vpc.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import aws.hands.on.AppTest;

import static aws.hands.on.vpc.services.VpcHandler.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class VpcHandlerTest extends AppTest{
    @Test
    void testCreateVpc() {
        String vpcId = createVpc(ec2Client);

        System.out.println(vpcId);
    }
}
