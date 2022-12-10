package aws.hands.on.credential;

import java.nio.file.Path;
import java.nio.file.Paths;

import software.amazon.awssdk.regions.Region;

public class CredentialsInfo {
    // for all
    public final static Region MY_REGION = Region.AP_NORTHEAST_1;
    // aurora info
    public static final String URL = "jdbc:postgresql://database-2-instance-1.cfj4ccwfp8dp.ap-northeast-1.rds.amazonaws.com:5432/postgres";
    public static final String USER = "postgres";
    public static final String PASSWORD = "postgres";
    // for ec2
    public static final String DEFAULT_KEY_PAIR_NAME = "key-for-default";
    public static final String EC2_FOLDER_PATH = "workspace/private-key-for-ec2";
    public static final Path BASE_FOLDER_PATH = Paths.get(System.getProperty("user.home"));
    public static final Path FOLDER_PATH = BASE_FOLDER_PATH.resolve(EC2_FOLDER_PATH);
    public static final String DEFAULT_SECURITY_GROUP_NAME = "default-security-group";
    public static final String DEFAULT_VPC_ID = "vpc-01e677f1b5a1323dd";

    private CredentialsInfo(){
    }

}
