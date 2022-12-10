package aws.hands.on.ec2.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.services.ec2.model.InstanceType;

import static aws.hands.on.ec2.configs.Ec2Config.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ec2InstanceDTO {
    private String amiId = DEFAULT_AMI_ID;
    private InstanceType instanceType = DEFAULT_INSTANCE_TYPE;
    private Integer createMaxCount = CREATE_MAX_COUNT;
    private Integer createMinCount = CREATE_MIN_COUNT;
    private String keyPairName = DEFAULT_KEY_PAIR_NAME;
    private String securityGroupName = DEFAULT_SECURITY_GROUP_NAME;
    private String tagName = DEFAULT_TAG_NAME;
}
