package aws.hands.on.sns.services;

import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.SnsException;
import software.amazon.awssdk.services.sns.model.SubscribeRequest;
import software.amazon.awssdk.services.sns.model.SubscribeResponse;

public class SnsSubscription {
    private SnsSubscription(){

    }

    public static void subscriptionEmail(SnsClient snsClient, String topicArn, String email) {
        try {
            SubscribeRequest subscribeRequest = SubscribeRequest.builder()
                .protocol("email")
                .endpoint(email)
                .returnSubscriptionArn(true)
                .topicArn(topicArn)
                .build();

            SubscribeResponse subscribeResponse = snsClient.subscribe(subscribeRequest);
            System.out.println("Subscription ARN: %s\nStatus is %s\n"
                .formatted(subscribeResponse.subscriptionArn(), subscribeResponse.sdkHttpResponse().statusCode()));

        } catch (SnsException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
    }
}
