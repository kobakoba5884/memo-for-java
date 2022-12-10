package aws.hands.on.ec2.configs;

import java.nio.file.Path;
import java.nio.file.Paths;

import software.amazon.awssdk.services.ec2.model.InstanceType;

public class Ec2Config {
    // for ec2
    public static final String DEFAULT_AMI_ID = "ami-072bfb8ae2c884cc4";
    public static final InstanceType DEFAULT_INSTANCE_TYPE = InstanceType.T2_MICRO;
    public static final Integer CREATE_MAX_COUNT = 1;
    public static final Integer CREATE_MIN_COUNT = 1;
    public static final String DEFAULT_KEY_PAIR_NAME = "key-for-default";
    public static final String DEFAULT_SECURITY_GROUP_NAME = "default-security-group";
    public static final String DEFAULT_VPC_ID = "vpc-01e677f1b5a1323dd";

    public static final String EC2_FOLDER_PATH = "workspace/private-key-for-ec2";
    public static final Path BASE_FOLDER_PATH = Paths.get(System.getProperty("user.home"));
    public static final Path FOLDER_PATH = BASE_FOLDER_PATH.resolve(EC2_FOLDER_PATH);
}
