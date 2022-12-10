package aws.hands.on.ec2.services;

import java.util.List;
import java.util.Optional;

import aws.hands.on.ec2.models.Ec2InstanceDTO;
import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.ec2.model.DescribeInstancesRequest;
import software.amazon.awssdk.services.ec2.model.DescribeInstancesResponse;
import software.amazon.awssdk.services.ec2.model.Ec2Exception;
import software.amazon.awssdk.services.ec2.model.Instance;
import software.amazon.awssdk.services.ec2.model.InstanceStateChange;
import software.amazon.awssdk.services.ec2.model.KeyPairInfo;
import software.amazon.awssdk.services.ec2.model.RunInstancesRequest;
import software.amazon.awssdk.services.ec2.model.RunInstancesResponse;
import software.amazon.awssdk.services.ec2.model.SecurityGroup;
import software.amazon.awssdk.services.ec2.model.StartInstancesRequest;
import software.amazon.awssdk.services.ec2.model.StopInstancesRequest;
import software.amazon.awssdk.services.ec2.model.TerminateInstancesRequest;
import software.amazon.awssdk.services.ec2.model.TerminateInstancesResponse;

import static aws.hands.on.ec2.services.Ec2SecurityGroupHandler.*;
import static aws.hands.on.ec2.services.Ec2KeyPairHandler.*;

import static aws.hands.on.credential.CredentialsInfo.DEFAULT_VPC_ID;

public class Ec2InstanceHandler{

    // create ec2 instance
    public static String createEc2Instance(Ec2Client ec2Client, Ec2InstanceDTO ec2InstanceDTO){
        prepareBeforeCreating(ec2Client, ec2InstanceDTO);

        String amiId = ec2InstanceDTO.getAmiId();

        RunInstancesRequest runInstancesRequest = RunInstancesRequest.builder()
            .imageId(amiId)
            .instanceType(ec2InstanceDTO.getInstanceType())
            .maxCount(ec2InstanceDTO.getCreateMaxCount())
            .minCount(ec2InstanceDTO.getCreateMinCount())
            .keyName(ec2InstanceDTO.getKeyPairName())
            .securityGroups(ec2InstanceDTO.getSecurityGroupName())
            .build();

        try{
            RunInstancesResponse runInstancesResponse = ec2Client.runInstances(runInstancesRequest);
            String instanceId = runInstancesResponse.instances().get(0).instanceId();

            // tagName.ifPresent(name -> createTag(ec2Client, instanceId, name));

            System.out.format("Successfully started EC2 Instance %s based on AMI %s\n", instanceId, amiId);
            
            return instanceId;
        }catch(Ec2Exception e){
            System.err.println(e.awsErrorDetails().errorMessage());
            return "";
        }
    }

    private static void prepareBeforeCreating(Ec2Client ec2Client, Ec2InstanceDTO ec2InstanceDTO){
        String securityGroupName = ec2InstanceDTO.getSecurityGroupName();

        Optional<SecurityGroup> securityGroup = getEC2SecurityGroupByName(ec2Client, securityGroupName);

        if(securityGroup.isEmpty()){
            createEC2SecurityGroup(ec2Client, securityGroupName, securityGroupName, DEFAULT_VPC_ID);
        }

        String keyPairName = ec2InstanceDTO.getKeyPairName();

        Optional<KeyPairInfo> keyPairInfo = getEC2KeyByKeyName(ec2Client, keyPairName);

        keyPairInfo.ifPresent(key -> deleteKeyPair(ec2Client, keyPairName));
        createEC2KeyPair(ec2Client, keyPairName);
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

    // start ec2 instance
    public static void startEC2Instance(Ec2Client ec2Client, String instanceId){
        try{
            StartInstancesRequest startInstancesRequest = StartInstancesRequest.builder()
                .instanceIds(instanceId)
                .build();

            ec2Client.startInstances(startInstancesRequest);

            System.out.printf("Successfully started instance %s\n", instanceId);
        }catch(Ec2Exception e){
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
    }

    public static void stopEC2Instance(Ec2Client ec2Client, String instanceId){
        try{
            StopInstancesRequest stopInstancesRequest = StopInstancesRequest.builder()
                .instanceIds(instanceId)
                .build();

            ec2Client.stopInstances(stopInstancesRequest);

            System.out.printf("Successfully stopped instance %s\n", instanceId);
        }catch(Ec2Exception e){
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
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
