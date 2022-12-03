package aws.hands.on.ec2;

import java.util.List;
import java.util.Optional;

import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.ec2.model.DescribeInstancesRequest;
import software.amazon.awssdk.services.ec2.model.DescribeInstancesResponse;
import software.amazon.awssdk.services.ec2.model.Ec2Exception;
import software.amazon.awssdk.services.ec2.model.Instance;
import software.amazon.awssdk.services.ec2.model.InstanceStateChange;
import software.amazon.awssdk.services.ec2.model.InstanceType;
import software.amazon.awssdk.services.ec2.model.KeyPairInfo;
import software.amazon.awssdk.services.ec2.model.RunInstancesRequest;
import software.amazon.awssdk.services.ec2.model.RunInstancesResponse;
import software.amazon.awssdk.services.ec2.model.SecurityGroup;
import software.amazon.awssdk.services.ec2.model.TerminateInstancesRequest;
import software.amazon.awssdk.services.ec2.model.TerminateInstancesResponse;

import static aws.hands.on.credential.CredentioalsInfo.KEY_PAIR_NAME;
import static aws.hands.on.credential.CredentioalsInfo.DEFAULT_SECURITY_GROUP_NAME;
import static aws.hands.on.ec2.Ec2KeyPairHandler.getEC2KeyByKeyName;
import static aws.hands.on.ec2.Ec2KeyPairHandler.createEC2KeyPair;
import static aws.hands.on.ec2.Ec2KeyPairHandler.deleteKeyPair;
import static aws.hands.on.ec2.Ec2SecurityGroupHandler.createEC2SecurityGroup;
import static aws.hands.on.ec2.Ec2SecurityGroupHandler.getEC2SecurityGroupByName;
import static aws.hands.on.ec2.Ec2TagHandler.createTag;

public class Ec2InstanceHandler {
    private final static Integer CREATE_COUNT = 1;
    private final static String AMI_ID = "ami-072bfb8ae2c884cc4"; // amazon linux 2

    private Ec2InstanceHandler(){

    }

    // create ec2 instance
    public static String createEc2Instance(Ec2Client ec2Client, String... tagNames){
        Optional<String> tagName = tagNames.length == 0 ? Optional.empty() : Optional.of(tagNames[0]);

        Optional<SecurityGroup> securityGroup = getEC2SecurityGroupByName(ec2Client, DEFAULT_SECURITY_GROUP_NAME);
        
        String securityGroupId = securityGroup.isPresent() ? securityGroup.get().groupId() : createEC2SecurityGroup(ec2Client, DEFAULT_SECURITY_GROUP_NAME, DEFAULT_SECURITY_GROUP_NAME);

        RunInstancesRequest runInstancesRequest = RunInstancesRequest.builder()
            .imageId(AMI_ID)
            .instanceType(InstanceType.T2_MICRO)
            .maxCount(CREATE_COUNT)
            .minCount(CREATE_COUNT)
            .keyName(KEY_PAIR_NAME)
            .securityGroupIds(securityGroupId)
            .build();
        
        Optional<KeyPairInfo> keyPairInfo = getEC2KeyByKeyName(ec2Client, KEY_PAIR_NAME);

        keyPairInfo.ifPresent(key -> deleteKeyPair(ec2Client, KEY_PAIR_NAME));
        createEC2KeyPair(ec2Client, KEY_PAIR_NAME);

        try{
            RunInstancesResponse runInstancesResponse = ec2Client.runInstances(runInstancesRequest);
            String instanceId = runInstancesResponse.instances().get(0).instanceId();

            tagName.ifPresent(name -> createTag(ec2Client, instanceId, name));

            System.out.format("Successfully started EC2 Instance %s based on AMI %s\n", instanceId, AMI_ID);
            
            return instanceId;
        }catch(Ec2Exception e){
            System.err.println(e.awsErrorDetails().errorMessage());
            return "";
        }
    }

    // get instances list
    public static Optional<List<Instance>> getInstancesList(Ec2Client ec2Client){
        List<Instance> result = null;

        try{
            DescribeInstancesResponse describeInstancesResponse = ec2Client.describeInstances();

            result = describeInstancesResponse.reservations().stream().filter(reservation -> reservation.hasInstances())
                .flatMap(reservation -> reservation.instances().stream()).toList();
            
            if(result.size() == 0){
                System.out.println("instances are nothing.");
                return Optional.empty();
            }

            result.stream().forEach(instance -> {
                System.out.println("Instance Id is " + instance.instanceId());
                System.out.println("Image id is "+ instance.imageId());
                System.out.println("Instance type is "+ instance.instanceType());
                System.out.println("Instance state name is "+ instance.state().name());
                System.out.println("monitoring information is "+ instance.monitoring().state());
            });

            return Optional.of(result);
        }catch(Ec2Exception e){
            System.err.println(e.awsErrorDetails().errorMessage());
            return Optional.empty();
        }
    }

    // get instance info
    public static Optional<Instance> getInstanceByInstanceId(Ec2Client ec2Client, String instanceId){
        try{
            DescribeInstancesRequest describeInstancesRequest = DescribeInstancesRequest.builder()
                .instanceIds(instanceId)
                .build();

            DescribeInstancesResponse describeInstancesResponse = ec2Client.describeInstances(describeInstancesRequest);
            Optional<Instance> result = describeInstancesResponse.reservations().stream().filter(reservation -> reservation.hasInstances())
                .flatMap(reservation -> reservation.instances().stream()).findAny();
            
            System.out.printf("found instance!! (%s)\n", instanceId);

            return result;
        }catch(Ec2Exception e){
            System.err.println(e.awsErrorDetails().errorMessage());

            return Optional.empty();
        }
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
