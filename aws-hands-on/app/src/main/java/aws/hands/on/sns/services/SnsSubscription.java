package aws.hands.on.sns.services;

import java.util.List;
import java.util.Optional;

import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.ConfirmSubscriptionRequest;
import software.amazon.awssdk.services.sns.model.ConfirmSubscriptionResponse;
import software.amazon.awssdk.services.sns.model.ListSubscriptionsRequest;
import software.amazon.awssdk.services.sns.model.ListSubscriptionsResponse;
import software.amazon.awssdk.services.sns.model.SnsException;
import software.amazon.awssdk.services.sns.model.SubscribeRequest;
import software.amazon.awssdk.services.sns.model.SubscribeResponse;
import software.amazon.awssdk.services.sns.model.Subscription;

public class SnsSubscription {
    private SnsSubscription(){

    }

    // subscript email
    public static String subscribeEmail(SnsClient snsClient, String topicArn, String email) {
        try {
            SubscribeRequest subscribeRequest = SubscribeRequest.builder()
                .protocol("email")
                .endpoint(email)
                .returnSubscriptionArn(true)
                .topicArn(topicArn)
                .build();

            SubscribeResponse subscribeResponse = snsClient.subscribe(subscribeRequest);

            String subscriptionArn = subscribeResponse.subscriptionArn();
            System.out.println("Subscription ARN: %s\nStatus is %s\n"
                .formatted(subscriptionArn, subscribeResponse.sdkHttpResponse().statusCode()));

            return subscriptionArn;
        } catch (SnsException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            return "";
        }
    }

    public static String subscribeTextSNS( SnsClient snsClient, String topicArn, String phoneNumber) {
        try {
            SubscribeRequest request = SubscribeRequest.builder()
                .protocol("sms")
                .endpoint(phoneNumber)
                .returnSubscriptionArn(true)
                .topicArn(topicArn)
                .build();

            SubscribeResponse result = snsClient.subscribe(request);

            String subscriptionArn = result.subscriptionArn();
            System.out.println("Subscription ARN: %s\nStatus is %s\n"
                .formatted(subscriptionArn, result.sdkHttpResponse().statusCode()));

            return subscriptionArn;
        } catch (SnsException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            return "";
        }
    }

    // get subscription list , not confirmation subscription
    public static Optional<List<Subscription>> getListSNSSubscriptions(SnsClient snsClient) {
        Optional<List<Subscription>> empty = Optional.empty();
        try {
            ListSubscriptionsRequest request = ListSubscriptionsRequest.builder()
                .build();

            ListSubscriptionsResponse result = snsClient.listSubscriptions(request);

            if(!result.hasSubscriptions()){
                System.out.println("subscription list is empty.");
                return empty;
            }

            List<Subscription> subscriptions = result.subscriptions();

            subscriptions.stream().forEach(System.out::println);

            return subscriptions.size() != 0 ? Optional.of(subscriptions) : empty;
        } catch (SnsException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            return empty;
        }
    }

    public static void confirmSubscription(SnsClient snsClient, String subscriptionToken, String topicArn) {
        try {
            ConfirmSubscriptionRequest request = ConfirmSubscriptionRequest.builder()
                .token(subscriptionToken)
                .topicArn(topicArn)
                .build();

            ConfirmSubscriptionResponse result = snsClient.confirmSubscription(request);
            System.out.println("\n\nStatus was " + result.sdkHttpResponse().statusCode() + "\n\nSubscription Arn: \n\n" + result.subscriptionArn());

        } catch (SnsException e) {
        System.err.println(e.awsErrorDetails().errorMessage());
        System.exit(1);
        }
    }
}
