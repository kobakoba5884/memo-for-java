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
import software.amazon.awssdk.services.sns.model.UnsubscribeRequest;
import software.amazon.awssdk.services.sns.model.UnsubscribeResponse;

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

            System.out.println(subscribeResponse.sdkHttpResponse().headers());

            System.out.println("Subscription ARN: %s\nStatus is %s\n"
                .formatted(subscriptionArn, subscribeResponse.sdkHttpResponse().statusCode()));

            return subscriptionArn;
        } catch (SnsException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            return "";
        }
    }

    // subtribe text SNS 
    public static String subscribeTextSNS( SnsClient snsClient, String topicArn, String phoneNumber) {
        try {
            SubscribeRequest request = SubscribeRequest.builder()
                .protocol("sms")
                .endpoint(phoneNumber)
                .returnSubscriptionArn(true)
                .topicArn(topicArn)
                .build();

            SubscribeResponse response = snsClient.subscribe(request);

            String subscriptionArn = response.subscriptionArn();
            System.out.println("Subscription ARN: %s\nStatus is %s\n"
                .formatted(subscriptionArn, response.sdkHttpResponse().statusCode()));

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

            ListSubscriptionsResponse response = snsClient.listSubscriptions(request);

            if(!response.hasSubscriptions()){
                System.out.println("subscription list is empty.");
                return empty;
            }

            List<Subscription> subscriptions = response.subscriptions();

            subscriptions.stream().forEach(System.out::println);

            return subscriptions.isEmpty() ? empty : Optional.of(subscriptions);
        } catch (SnsException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            return empty;
        }
    }

    // remove subscription
    public static void removeSubscription(SnsClient snsClient, String subscriptionArn) {
        try {
            UnsubscribeRequest request = UnsubscribeRequest.builder()
                .subscriptionArn(subscriptionArn)
                .build();

            UnsubscribeResponse response = snsClient.unsubscribe(request);

            System.out.println("Status was %s\nSubscription was removed for %s\n"
                .formatted(response.sdkHttpResponse().statusCode(), subscriptionArn));
        } catch (SnsException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
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
