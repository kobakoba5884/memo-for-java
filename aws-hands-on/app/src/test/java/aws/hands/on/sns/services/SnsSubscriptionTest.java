package aws.hands.on.sns.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import aws.hands.on.sns.SnsTest;
import software.amazon.awssdk.services.sns.model.Subscription;

import static aws.hands.on.sns.services.SnsSubscription.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import static aws.hands.on.credential.CredentialsInfo.EMAIL;
import static aws.hands.on.credential.CredentialsInfo.PHONE;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SnsSubscriptionTest extends SnsTest{
    @Test
    void testSubEmail() {
        subscribeEmail(snsClient, topicArn, EMAIL);
    }

    @Test
    void testSubscribeTextSNS() {
        subscribeTextSNS(snsClient, topicArn, PHONE);
    }

    @Test
    void testGetListSNSSubscriptions() {
        Optional<List<Subscription>> subscriptions = getListSNSSubscriptions(snsClient);

        assertTrue(subscriptions.isPresent());
    }

    @Test
    void testRemoveSubscription() {
        String subscriptionArn = "arn:aws:sns:ap-northeast-1:779004463807:test-topic:cc2f9f9b-4988-4579-b635-fc66d19014ba";
        removeSubscription(snsClient, subscriptionArn);

        Optional<Subscription> result = getListSNSSubscriptions(snsClient).get().stream()
            .filter(sub -> sub.subscriptionArn().equals(subscriptionArn)).findAny();

        assertTrue(result.isEmpty());
    }
}
