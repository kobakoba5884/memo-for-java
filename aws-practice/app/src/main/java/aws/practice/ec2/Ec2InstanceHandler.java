package aws.practice.ec2;

import java.util.List;
import java.util.Optional;

import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.ec2.model.CreateTagsRequest;
import software.amazon.awssdk.services.ec2.model.Ec2Exception;
import software.amazon.awssdk.services.ec2.model.InstanceStateChange;
import software.amazon.awssdk.services.ec2.model.InstanceType;
import software.amazon.awssdk.services.ec2.model.KeyPairInfo;
import software.amazon.awssdk.services.ec2.model.RunInstancesRequest;
import software.amazon.awssdk.services.ec2.model.RunInstancesResponse;
import software.amazon.awssdk.services.ec2.model.Tag;
import software.amazon.awssdk.services.ec2.model.TerminateInstancesRequest;
import software.amazon.awssdk.services.ec2.model.TerminateInstancesResponse;

import static aws.practice.credential.CredentioalsInfo.KEY_PAIR_NAME;
import static aws.practice.ec2.Ec2KeyPairHandler.getEC2KeyByKeyName;
import static aws.practice.ec2.Ec2KeyPairHandler.createEC2KeyPair;

public class Ec2InstanceHandler {
    private final static Integer CREATE_COUNT = 1;
    private final static String AMI_ID = "ami-072bfb8ae2c884cc4"; // amazon linux 2

    private Ec2InstanceHandler(){

    }

    // create ec2 instance
    public static String createEc2Instance(Ec2Client ec2Client, String name){
        RunInstancesRequest runInstancesRequest = RunInstancesRequest.builder()
            .imageId(AMI_ID)
            .instanceType(InstanceType.T2_MICRO)
            .maxCount(CREATE_COUNT)
            .minCount(CREATE_COUNT)
            .keyName(KEY_PAIR_NAME)
            .build();
        
        Optional<KeyPairInfo> keyPairInfo = getEC2KeyByKeyName(ec2Client, KEY_PAIR_NAME);

        if(keyPairInfo.isEmpty()){
            createEC2KeyPair(ec2Client, KEY_PAIR_NAME);
        }

        RunInstancesResponse runInstancesResponse = ec2Client.runInstances(runInstancesRequest);
        String instanceId = runInstancesResponse.instances().get(0).instanceId();

        Tag tag = Tag.builder()
            .key("Name")
            .value(name)
            .build();

        CreateTagsRequest tagsRequest = CreateTagsRequest.builder()
            .resources(instanceId)
            .tags(tag)
            .build();
        
        try{
            ec2Client.createTags(tagsRequest);
            System.out.format(  "Successfully started EC2 Instance %s based on AMI %s\n", instanceId, AMI_ID);
            return instanceId;
        }catch(Ec2Exception e){
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }

        return "";
    }

    // terminate ec2 instance
    public static void terminateEC2Instance(Ec2Client ec2Client, String instanceId){
        try{
            TerminateInstancesRequest terminateInstancesRequest = TerminateInstancesRequest.builder()
                .instanceIds(instanceId)
                .build();
            
            TerminateInstancesResponse terminateInstancesResponse = ec2Client.terminateInstances(terminateInstancesRequest);
            List<InstanceStateChange> instanceStateChanges = terminateInstancesResponse.terminatingInstances();

            instanceStateChanges.stream().forEach(state -> {
                System.out.printf("The ID of the terminated instance is %s\n", state.instanceId());
            });
        }catch(Ec2Exception e){
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
    }
}
