package aws.hands.on.ec2;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import aws.hands.on.AppTest;

import static aws.hands.on.credential.CredentialsInfo.KEY_PAIR_NAME;
import static aws.hands.on.ec2.Ec2KeyPairHandler.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class Ec2KeyPairHandlerTest extends AppTest{
    @Test
    void testAllDeleteKeys() {

    }

    @Test
    void testCreateEC2KeyPair() {
        createEC2KeyPair(ec2Client, KEY_PAIR_NAME);
    }

    @Test
    void testDeleteKeyPair() {

    }

    @Test
    void testGetEC2KeyByKeyName() {

    }

    @Test
    void testGetEC2KeyPairs() {

    }
}
