package aws.hands.on.ec2.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import software.amazon.awssdk.services.ec2.model.CreateTagsResponse;

import static aws.hands.on.ec2.services.Ec2TagHandler.*;

import java.util.Optional;

@TestInstance(TestInstance.Lifecycle.PER_CLASS) 
public class Ec2TagHandlerTest extends Ec2Test{

    @Test
    void testCreateTag() {
        String resourceId = "sg-032b7b4167c528552";
        String tagName = "";

        Optional<CreateTagsResponse> createTagsResponse = createTag(ec2Client, resourceId, tagName);
        
        createTagsResponse.ifPresent(System.out::println);
    }
}
