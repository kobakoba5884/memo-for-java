/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package aws.hands.on;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.services.ec2.Ec2Client;

import static aws.hands.on.credential.CredentialsInfo.MY_REGION;

public class AppTest {
    protected Ec2Client ec2Client;

    @BeforeAll
    void setupAppTest(){
        this.ec2Client = Ec2Client.builder()
            .region(MY_REGION)
            .credentialsProvider(ProfileCredentialsProvider.create())
            .build();
    }

    @AfterAll
    void teardownAppTest(){
        ec2Client.close();
    }
}
