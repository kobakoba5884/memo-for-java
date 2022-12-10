package aws.hands.on.iam;

import software.amazon.awssdk.core.waiters.WaiterResponse;
import software.amazon.awssdk.services.iam.IamClient;
import software.amazon.awssdk.services.iam.model.CreateUserRequest;
import software.amazon.awssdk.services.iam.model.CreateUserResponse;
import software.amazon.awssdk.services.iam.model.DeleteUserRequest;
import software.amazon.awssdk.services.iam.model.DeleteUserResponse;
import software.amazon.awssdk.services.iam.model.GetUserRequest;
import software.amazon.awssdk.services.iam.model.GetUserResponse;
import software.amazon.awssdk.services.iam.model.IamException;
import software.amazon.awssdk.services.iam.model.IamResponseMetadata;
import software.amazon.awssdk.services.iam.model.UpdateUserRequest;
import software.amazon.awssdk.services.iam.waiters.IamWaiter;

public class IamUserHandler {
    private IamUserHandler(){

    }

    // create iam user
    public static String createIamUser(IamClient iamClient, String userName){

        try{
            IamWaiter iamWaiter = iamClient.waiter();

            CreateUserRequest createUserRequest = CreateUserRequest.builder()
                .userName(userName)
                .build();
                // .permissionsBoundary()

            CreateUserResponse createUserResponse = iamClient.createUser(createUserRequest);

            String createdUserName = createUserResponse.user().userName();

            System.out.printf("The new user (%s) was successfully created!!\n", createdUserName);

            GetUserRequest getUserRequest = GetUserRequest.builder()
                .userName(createdUserName)
                .build();

            WaiterResponse<GetUserResponse> waiterResponse = iamWaiter.waitUntilUserExists(getUserRequest);
            waiterResponse.matched().response().ifPresent(System.out::println);

            return createdUserName;
        }catch (IamException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            return "";
        }
    }

    // update iam user name
    public static void updateIamUserName(IamClient iamClient, String userName, String newUserName){

        try {
            UpdateUserRequest request = UpdateUserRequest.builder()
                .userName(userName)
                .newUserName(newUserName)
                .build();

            iamClient.updateUser(request);
            System.out.printf("Successfully updated user to username %s", newUserName);

        } catch (IamException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            throw e;
        }
    }

    // delete iam user
    public static void deleteIamUser(IamClient iamClient, String userName){

        try {
            DeleteUserRequest request = DeleteUserRequest.builder()
                .userName(userName)
                .build();

            DeleteUserResponse deleteUserResponse = iamClient.deleteUser(request);

            IamResponseMetadata iamResponseMetadata = deleteUserResponse.responseMetadata();

            System.out.printf("Successfully deleted IAM user (%s)\n", userName);
            System.out.println(iamResponseMetadata);
        } catch (IamException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            return;
        }
    }
}
