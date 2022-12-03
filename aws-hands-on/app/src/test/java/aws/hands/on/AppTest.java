/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package aws.hands.on;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.services.ec2.Ec2Client;

import static aws.hands.on.credential.CredentioalsInfo.MY_REGION;

public class AppTest {
    protected Ec2Client ec2Client;

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
}