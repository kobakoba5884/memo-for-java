package aws.hands.on.sns;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import software.amazon.awssdk.http.SdkHttpResponse;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.CreateTopicRequest;
import software.amazon.awssdk.services.sns.model.CreateTopicResponse;
import software.amazon.awssdk.services.sns.model.DeleteTopicRequest;
import software.amazon.awssdk.services.sns.model.DeleteTopicResponse;
import software.amazon.awssdk.services.sns.model.GetTopicAttributesRequest;
import software.amazon.awssdk.services.sns.model.GetTopicAttributesResponse;
import software.amazon.awssdk.services.sns.model.ListTopicsRequest;
import software.amazon.awssdk.services.sns.model.ListTopicsResponse;
import software.amazon.awssdk.services.sns.model.SnsException;
import software.amazon.awssdk.services.sns.model.Topic;

public class SnsTopicHandler {
    private SnsTopicHandler(){

    }

    // create sns topic
    public static String createSNSTopic(SnsClient snsClient, String topicName){
        try{
            CreateTopicRequest createTopicRequest = CreateTopicRequest.builder()
                .name(topicName)
                .build();

            CreateTopicResponse createTopicResponse = snsClient.createTopic(createTopicRequest);

            System.out.printf("successfully created sns topic(%s).\n", topicName);

            String topicArn = createTopicResponse.topicArn();

            return topicArn;
        }catch(SnsException e){
            System.err.println(e.awsErrorDetails().errorMessage());
            return "";
        }
    }

    // get topic list
    public static Optional<List<Topic>> getSNSTopicList(SnsClient snsClient){
        try{
            ListTopicsRequest listTopicsRequest = ListTopicsRequest.builder().build();

            ListTopicsResponse listTopicsResponse = snsClient.listTopics(listTopicsRequest);

            if(!listTopicsResponse.hasTopics()){
                System.out.println("topic list is empty.");
                return Optional.empty();
            }

            List<Topic> topics = listTopicsResponse.topics();

            topics.stream().forEach(System.out::println);

            return Optional.of(topics);
        }catch (SnsException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            return Optional.empty();
        }
    }

    public static Optional<Map<String, String>> getSNSTopicAttribute(SnsClient snsClient, String topicArn){
        try{
            GetTopicAttributesRequest getTopicAttributesRequest = GetTopicAttributesRequest.builder()
                .topicArn(topicArn)
                .build();

            GetTopicAttributesResponse getTopicAttributesResponse = snsClient.getTopicAttributes(getTopicAttributesRequest);

            if(!getTopicAttributesResponse.hasAttributes()){
                System.out.println("topic attributes is empty.");
                return Optional.empty();
            }

            Map<String, String> topicAttributes = getTopicAttributesResponse.attributes();

            System.out.printf("Status is %s\n", getTopicAttributesResponse.sdkHttpResponse().statusCode());
            System.out.println(topicAttributes);

            return Optional.of(topicAttributes);
        }catch (SnsException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            return Optional.empty();
        }
    }

    public static void updateSNSTopicName(){
        
    }

    // delete sns topic
    public static void deleteSNSTopic(SnsClient snsClient, String topicArn){
        try{
            DeleteTopicRequest deleteTopicRequest = DeleteTopicRequest.builder()
                .topicArn(topicArn)
                .build();

            DeleteTopicResponse topicResponse = snsClient.deleteTopic(deleteTopicRequest);

            SdkHttpResponse sdkHttpResponse = topicResponse.sdkHttpResponse();

            int statusCode = sdkHttpResponse.statusCode();
            String responseDate = sdkHttpResponse.headers().get("Date").get(0);

            System.out.printf("successfully deleted sns topic(%s) at %s. Status was %d.\n", topicArn, responseDate, statusCode);
        }catch(SnsException e){
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
    }
}
