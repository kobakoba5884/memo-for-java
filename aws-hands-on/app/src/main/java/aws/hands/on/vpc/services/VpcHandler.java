package aws.hands.on.vpc.services;

import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.ec2.model.CreateVpcRequest;
import software.amazon.awssdk.services.ec2.model.CreateVpcResponse;
import software.amazon.awssdk.services.ec2.model.Ec2Exception;

public class VpcHandler {
    private VpcHandler(){

    }

    // create vpc
    public static String createVpc(Ec2Client ec2Client){
        try{
            CreateVpcRequest createVpcRequest = CreateVpcRequest.builder()
                .ipv4IpamPoolId("ipam-pool-0310350124239d3c7")
                .ipv4NetmaskLength(16)
                .build();

            CreateVpcResponse createVpcResponse = ec2Client.createVpc(createVpcRequest);

            String vpcId = createVpcResponse.vpc().vpcId();

            return vpcId;
        }catch(Ec2Exception e){
            System.err.println(e.awsErrorDetails().errorMessage());
            return "";
        }
    }
}
