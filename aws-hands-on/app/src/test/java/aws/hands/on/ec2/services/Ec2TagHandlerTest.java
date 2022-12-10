package aws.hands.on.ec2.services;

import org.junit.jupiter.api.Test;

import aws.hands.on.AppTest;

import software.amazon.awssdk.services.ec2.model.CreateTagsResponse;

import static aws.hands.on.ec2.services.Ec2TagHandler.*;

import java.util.Optional;

public class Ec2TagHandlerTest extends AppTest{

    @Test
    void testCreateTag() {
        String resourceId = "sg-032b7b4167c528552";
        String tagName = "";

        Optional<CreateTagsResponse> createTagsResponse = createTag(ec2Client, resourceId, tagName);
        
        createTagsResponse.ifPresent(System.out::println);
    }
}
