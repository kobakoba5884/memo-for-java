package aws.hands.on.sns.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

import aws.hands.on.sns.SnsTest;
import aws.hands.on.sns.config.TopicAttributeName;
import software.amazon.awssdk.services.sns.model.Topic;

import static aws.hands.on.sns.services.SnsTopicHandler.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SnsTopicHandlerTest extends SnsTest{

    @Test
    @Order(1)
    void testCreateSNSTopic() {
        String topicArn = createSNSTopic(snsClient, topicName);

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
    void getArnByTopicNameTest(){
        String getTopicArn = getArnByTopicName(snsClient, topicName);

        System.out.println(getTopicArn);

        String dummyTopicArn = getArnByTopicName(snsClient, "topicName");
        
        assertEquals("", dummyTopicArn);
    }

    @Test
    @Order(4)
    void testGetSNSTopicAttribute() {
        String dummyTopicArn = "dummy-topic-arn";
        Optional<Map<String, String>> topicAttributes = getSNSTopicAttributes(snsClient, topicArn);

        assertTrue(topicAttributes.isPresent());
        assertTrue(getSNSTopicAttributes(snsClient, dummyTopicArn).isEmpty());
    }

    @Test
    @Order(5)
    void testSetTopicAttributes() {
        setTopicAttributes(snsClient, TopicAttributeName.DISPLAY_NAME, topicArn, topicName);
    }

    @Test
    @Order(6)
    void testPublishTopic() {
        String message = "test";
        publishTopic(snsClient, message, message, topicArn);
    }

    @Test
    @Order(7)
    void testDeleteSNSTopic() {
        deleteSNSTopic(snsClient, topicArn);
        deleteSNSTopic(snsClient, topicArn);
    }

    @Test
    void testUpdateSNSTopicName() {
        
    }
}
