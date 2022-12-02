package aws.hands.on.ec2;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.ec2.model.CreateTagsResponse;

import java.util.Optional;

import static aws.hands.on.credential.CredentioalsInfo.MY_REGION;
import static aws.hands.on.ec2.Ec2TagHandler.*;

public class Ec2TagHandlerTest {
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
    void testCreateTag() {
        String resouceId = "sg-032b7b4167c528552";
        String tagName = "";

        Optional<CreateTagsResponse> createTagsResponse = createTag(ec2Client, resouceId, tagName);
        
        createTagsResponse.ifPresent(System.out::println);
    }
}
