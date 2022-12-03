package aws.hands.on.ec2;

import org.junit.jupiter.api.Test;

import aws.hands.on.AppTest;

import software.amazon.awssdk.services.ec2.model.CreateTagsResponse;

import java.util.Optional;

import static aws.hands.on.ec2.Ec2TagHandler.*;

public class Ec2TagHandlerTest extends AppTest{

    @Test
    void testCreateTag() {
        String resouceId = "sg-032b7b4167c528552";
        String tagName = "";

        Optional<CreateTagsResponse> createTagsResponse = createTag(ec2Client, resouceId, tagName);
        
        createTagsResponse.ifPresent(System.out::println);
    }
}
