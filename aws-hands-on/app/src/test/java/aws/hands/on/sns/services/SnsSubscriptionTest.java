package aws.hands.on.sns.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import aws.hands.on.sns.SnsTest;

import static aws.hands.on.sns.services.SnsSubscription.*;

import static aws.hands.on.credential.CredentialsInfo.EMAIL;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SnsSubscriptionTest extends SnsTest{
    @Test
    void testSubEmail() {
        subscriptionEmail(snsClient, topicArn, EMAIL);
    }
}
