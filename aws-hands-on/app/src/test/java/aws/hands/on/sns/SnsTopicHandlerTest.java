package aws.hands.on.sns;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;

import software.amazon.awssdk.services.sns.model.Topic;

import static aws.hands.on.sns.SnsTopicHandler.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SnsTopicHandlerTest extends SnsTest{
    private String topicName = "test";
    private String topicArn;

    @Test
    @Order(1)
    void testCreateSNSTopic() {
        this.topicArn = createSNSTopic(snsClient, topicName);

        assertFalse(topicArn.isEmpty());
    }

    @Test
    @Order(2)
    void testGetSNSTopicList() {
        Optional<List<Topic>> topics = getSNSTopicList(snsClient);

        assertTrue(topics.isPresent());
    }

    @Test
    @Order(3)
    void testGetSNSTopicAttribute() {
        String dummyTopicArn = "dummy-topic-arn";
        Optional<Map<String, String>> topicAttributes = getSNSTopicAttribute(snsClient, topicArn);

        assertTrue(topicAttributes.isPresent());
        assertTrue(getSNSTopicAttribute(snsClient, dummyTopicArn).isEmpty());
    }

    @Test
    @Order(4)
    void testDeleteSNSTopic() {
        deleteSNSTopic(snsClient, topicArn);
        deleteSNSTopic(snsClient, topicArn);
    }
}
