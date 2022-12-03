package aws.hands.on.rds;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.rds.RdsClient;
import software.amazon.awssdk.services.rds.model.CreateDbInstanceRequest;
import software.amazon.awssdk.services.rds.model.CreateDbInstanceResponse;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;

public class RdsHandlerV2 {
    private static final Region region = Region.AP_NORTHEAST_1;

    private RdsHandlerV2(){
    }

    // create database
    public static void createDatabaseInstance(RdsClient rdsClient, String dbInstanceIdentifier, String dbName, String engineName, String instanceClass, String engineVersion, String masterUsername, String masterUserPassword){
        CreateDbInstanceRequest instanceRequest = CreateDbInstanceRequest.builder()
            .dbInstanceIdentifier(dbInstanceIdentifier)
            .allocatedStorage(100) // The minimum value is 100 GiB and the maximum value is 65,536 GiB
            .dbName(dbName)
            .engine(engineName)
            .dbInstanceClass(instanceClass) // The compute and memory capacity of the instance
            .engineVersion(engineVersion)
            .storageType("standard")
            .masterUsername(masterUsername)
            .masterUserPassword(masterUserPassword)
            .build();

        CreateDbInstanceResponse instanceResponse = rdsClient.createDBInstance(instanceRequest);

        System.out.format("The status is %s\n",instanceResponse.dbInstance().dbInstanceStatus());
    }

    public static void main(String[] args) {
        RdsClient rdsClient = RdsClient.builder()
            .region(region)
            .credentialsProvider(ProfileCredentialsProvider.create())
            .build();

        rdsClient.close();
    }
}
