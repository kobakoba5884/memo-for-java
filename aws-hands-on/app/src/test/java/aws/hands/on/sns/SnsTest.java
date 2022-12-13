package aws.hands.on.sns;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.services.sns.SnsClient;

import static aws.hands.on.credential.CredentialsInfo.MY_REGION;
import static aws.hands.on.sns.services.SnsTopicHandler.*;

public class SnsTest {
    protected SnsClient snsClient;
    protected String topicName = "test-topic";
    protected String topicArn;

    @BeforeAll
    void setupAppTest(){
        this.snsClient = SnsClient.builder()
            .region(MY_REGION)
            .credentialsProvider(ProfileCredentialsProvider.create())
            .build();
        this.topicArn = createSNSTopic(snsClient, topicName);
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
