package aws.ec2;

import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.ec2.model.AuthorizeSecurityGroupIngressRequest;
import software.amazon.awssdk.services.ec2.model.CreateSecurityGroupRequest;
import software.amazon.awssdk.services.ec2.model.CreateSecurityGroupResponse;
import software.amazon.awssdk.services.ec2.model.Ec2Exception;

import static aws.credential.CredentioalsInfo.DEFAULT_VPC_ID;

public class Ec2SecurityGroupHandler {
    
    public static String createEC2SecurityGroup(Ec2Client ec2Client, String groupName, String groupDesc){
        try{
            CreateSecurityGroupRequest createSecurityGroupRequest = CreateSecurityGroupRequest.builder()
                .groupName(groupName)
                .description(groupDesc)
                .vpcId(DEFAULT_VPC_ID)
                .build();

            CreateSecurityGroupResponse createSecurityGroupResponse = ec2Client.createSecurityGroup(createSecurityGroupRequest);

            // AuthorizeSecurityGroupIngressRequest authorizeSecurityGroupIngressRequest = AuthorizeSecurityGroupIngressRequest.builder()
            //     .gr
        }catch(Ec2Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }

        
        return null;
    }
}
