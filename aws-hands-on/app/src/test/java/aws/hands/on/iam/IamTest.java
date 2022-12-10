package aws.hands.on.iam;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.iam.IamClient;

public class IamTest {
    protected IamClient iamClient;

    @BeforeAll
    void setupAppTest(){
        this.iamClient = IamClient.builder()
            .region(Region.AWS_GLOBAL)
            .credentialsProvider(ProfileCredentialsProvider.create())
            .build();
    }

    @AfterAll
    void teardownAppTest(){
        this.iamClient.close();
    }
}
