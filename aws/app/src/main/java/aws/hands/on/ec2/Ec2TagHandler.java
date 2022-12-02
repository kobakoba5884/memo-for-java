package aws.hands.on.ec2;

import java.util.Optional;

import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.ec2.model.CreateTagsRequest;
import software.amazon.awssdk.services.ec2.model.CreateTagsResponse;
import software.amazon.awssdk.services.ec2.model.Ec2Exception;
import software.amazon.awssdk.services.ec2.model.Tag;

public class Ec2TagHandler {

    private Ec2TagHandler(){

    }
    
    // create tag for resource
    public static Optional<CreateTagsResponse> createTag(Ec2Client ec2Client, String resouceId, String tagName){
        // generate tagname
        try{
            Tag tag = Tag.builder()
                .key("Name")
                .value(tagName)
                .build();

            CreateTagsRequest tagsRequest = CreateTagsRequest.builder()
                .resources(resouceId)
                .tags(tag)
                .build();
    
        
            CreateTagsResponse createTagsResponse = ec2Client.createTags(tagsRequest);

            System.out.format("Successfully created %s at %s\n", tagName, resouceId);

            return Optional.of(createTagsResponse);
        }catch(Ec2Exception e){
            System.err.println(e.awsErrorDetails().errorMessage());
            return Optional.empty();
        }
    }
}
