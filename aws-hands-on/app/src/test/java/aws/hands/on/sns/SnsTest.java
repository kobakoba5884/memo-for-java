package aws.hands.on.sns;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.services.sns.SnsClient;

import static aws.hands.on.credential.CredentialsInfo.MY_REGION;

public class SnsTest {
    protected SnsClient snsClient;

    @BeforeAll
    void setupAppTest(){
        this.snsClient = SnsClient.builder()
            .region(MY_REGION)
            .credentialsProvider(ProfileCredentialsProvider.create())
            .build();
    }

    @BeforeEach
    void init() throws InterruptedException{
        System.out.println("next to test!!");
    }

    @AfterAll
    void teardownAppTest(){
        this.snsClient.close();
    }
}
