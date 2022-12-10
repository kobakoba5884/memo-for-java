package aws.hands.on.ec2.services;

import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.ec2.model.AuthorizeSecurityGroupIngressRequest;
import software.amazon.awssdk.services.ec2.model.AuthorizeSecurityGroupIngressResponse;
import software.amazon.awssdk.services.ec2.model.CreateSecurityGroupRequest;
import software.amazon.awssdk.services.ec2.model.CreateSecurityGroupResponse;
import software.amazon.awssdk.services.ec2.model.DeleteSecurityGroupRequest;
import software.amazon.awssdk.services.ec2.model.DescribeSecurityGroupsRequest;
import software.amazon.awssdk.services.ec2.model.DescribeSecurityGroupsResponse;
import software.amazon.awssdk.services.ec2.model.Ec2Exception;
import software.amazon.awssdk.services.ec2.model.IpPermission;
import software.amazon.awssdk.services.ec2.model.IpRange;
import software.amazon.awssdk.services.ec2.model.SecurityGroup;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import io.ipinfo.api.IPinfo;
import io.ipinfo.api.errors.RateLimitedException;
import io.ipinfo.api.model.IPResponse;

public class Ec2SecurityGroupHandler {

    private Ec2SecurityGroupHandler(){

    }
    
    // create security group for ec2 instance
    public static String createEC2SecurityGroup(Ec2Client ec2Client, String groupName, String groupDesc, String vpcId){
        String result = "";

        try{
            CreateSecurityGroupRequest createSecurityGroupRequest = CreateSecurityGroupRequest.builder()
                .groupName(groupName)
                .description(groupDesc)
                .vpcId(vpcId)
                .build();

            CreateSecurityGroupResponse createSecurityGroupResponse = ec2Client.createSecurityGroup(createSecurityGroupRequest);

            String securityGroupId = createSecurityGroupResponse.groupId();
            List<IpPermission> ipPermissions = createDefaultBoundRules();

            AuthorizeSecurityGroupIngressRequest authorizeSecurityGroupIngressRequest = AuthorizeSecurityGroupIngressRequest.builder()
                .groupName(groupName)
                .ipPermissions(ipPermissions)
                .build();

            @SuppressWarnings("unused")
            AuthorizeSecurityGroupIngressResponse authorizeSecurityGroupIngressResponse = ec2Client.authorizeSecurityGroupIngress(authorizeSecurityGroupIngressRequest);

            System.out.printf("Successfully added ingress policy to Security Group %s\n", groupName);

            // createTag(ec2Client, securityGroupId, groupName);

            return securityGroupId;
        }catch(Ec2Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());

            return result;
        }
    }

    public static List<IpPermission> createDefaultBoundRules(){
        String targetIp = getMyGlobalIpAddress();

        IpRange ipRange = IpRange.builder()
            .cidrIp(targetIp)
            .build();

        Map<Integer, String> initInfos = new HashMap<>();

        initInfos.put(80, "tcp");
        initInfos.put(22, "tcp");

        List<IpPermission> ipPermissions = initInfos.entrySet().stream().map(initInfo -> 
            IpPermission.builder()
                .ipProtocol(initInfo.getValue())
                .toPort(initInfo.getKey())
                .fromPort(initInfo.getKey())
                .ipRanges(ipRange)
                .build()
        ).toList();

        return ipPermissions;   
    }

    // delete securityGroup
    public static void deleteEC2SecurityGroup(Ec2Client ec2Client, String groupId) {
        try {
            DeleteSecurityGroupRequest deleteSecurityGroupRequest  = DeleteSecurityGroupRequest.builder()
                .groupId(groupId)
                .build();

            ec2Client.deleteSecurityGroup(deleteSecurityGroupRequest);

            System.out.printf("Successfully deleted Security Group with id %s\n", groupId);

        } catch (Ec2Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
    }

    // get security group list
    public static Optional<List<SecurityGroup>> getEC2SecurityGroups(Ec2Client ec2Client) {
        try {
            DescribeSecurityGroupsResponse describeSecurityGroupsResponse = ec2Client.describeSecurityGroups();

            if(!describeSecurityGroupsResponse.hasSecurityGroups()){
                System.out.println("security groups are nothing.");
                return Optional.empty();
            }
            
            List<SecurityGroup> securityGroups = describeSecurityGroupsResponse.securityGroups();

            securityGroups.stream().forEach(securityGroup -> {
                System.out.printf("Found Security Group with id %s, vpc id %s and description %s\n",
                    securityGroup.groupId(),
                    securityGroup.vpcId(),
                    securityGroup.description());
            });

            return Optional.of(securityGroups);
        } catch (Ec2Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            return Optional.empty();
        }
   }

    // get security group by id
    public static Optional<SecurityGroup> getEC2SecurityGroupById(Ec2Client ec2Client, String securityGroupId){
        try {
            DescribeSecurityGroupsRequest describeSecurityGroupsRequest = DescribeSecurityGroupsRequest.builder()
                .groupIds(securityGroupId)
                .build();

            DescribeSecurityGroupsResponse describeSecurityGroupsResponse = ec2Client.describeSecurityGroups(describeSecurityGroupsRequest);
            
            List<SecurityGroup> securityGroups = describeSecurityGroupsResponse.securityGroups();

            System.out.printf("found securityGroup by %s !!\n", securityGroupId);

            return Optional.of(securityGroups.get(0));
        } catch (Ec2Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            return Optional.empty();
        }
    }

    // get security group by name
    public static Optional<SecurityGroup> getEC2SecurityGroupByName(Ec2Client ec2Client, String securityGroupName){
        try {
            DescribeSecurityGroupsRequest describeSecurityGroupsRequest = DescribeSecurityGroupsRequest.builder()
                .groupNames(securityGroupName)
                .build();

            DescribeSecurityGroupsResponse describeSecurityGroupsResponse = ec2Client.describeSecurityGroups(describeSecurityGroupsRequest);
            
            List<SecurityGroup> securityGroups = describeSecurityGroupsResponse.securityGroups();

            System.out.printf("found securityGroup by %s !!\n", securityGroupName);

            return Optional.of(securityGroups.get(0));
        } catch (Ec2Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            return Optional.empty();
        }
    }

    // get global ip address
    private static String getMyGlobalIpAddress(){
        IPinfo ipInfo = new IPinfo.Builder().build();
        String result = "0.0.0.0/0";

        try {
            IPResponse response = ipInfo.lookupIP("");

            result = String.format("%s/32", response.getIp());

            System.out.println("successfully get your global ip address!!");
        } catch (RateLimitedException e) {
            System.err.println(e.getMessage());
            return result;
        }

        return result;
    }
}
