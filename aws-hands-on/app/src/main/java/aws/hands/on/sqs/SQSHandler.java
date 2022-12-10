package aws.hands.on.sqs;

import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.AmazonSQSException;
import com.amazonaws.services.sqs.model.CreateQueueRequest;
import com.amazonaws.services.sqs.model.ListQueuesRequest;
import com.amazonaws.services.sqs.model.ListQueuesResult;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageRequest;

public class SQSHandler {
    // generate sqs client
    private static final AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();

    private SQSHandler(){

    }

    // create queue
    public static void createOriginalQueue(String queueName){
        CreateQueueRequest create_request = new CreateQueueRequest(queueName)
            .addAttributesEntry("DelaySeconds", "0")
            .addAttributesEntry("VisibilityTimeout", "0")
            .addAttributesEntry("MessageRetentionPeriod", "86400");

        if(queueName.endsWith(".fifo")){
            create_request.addAttributesEntry("FifoQueue", "true")
                .addAttributesEntry("ContentBasedDeduplication", "true");
        }

        try {
            sqs.createQueue(create_request);
        } catch (AmazonSQSException e) {
            if (!e.getErrorCode().equals("QueueAlreadyExists")) {
                throw e;
            }
        }
        System.out.format("created %s\n", queueName);
    }

    // Get the URL for a queue
    public static String getQueueUrl(String queueName){
        String queue_url = sqs.getQueueUrl(queueName).getQueueUrl();

        return queue_url;
    }

    // Delete the Queue
    public static void deleteOriginalQueue(String queueName){
        String queueUrl = sqs.getQueueUrl(queueName).getQueueUrl();
        sqs.deleteQueue(queueUrl);
        System.out.format("delete %s(%s)\n",queueName, queueUrl);
    }

    // Delete all queue
    public static void deleteAllQueue(){
        List<String> urls = listOriginalQueues();

        urls.stream().forEach((url) -> {
            sqs.deleteQueue(url);
            // sqs.getQueueAttributes(null)
            System.out.format("delete queue(%s)\n", url);
        });

        System.err.println("deleted all queue.");
    }

    // List your queues
    public static List<String> listOriginalQueues(){
        ListQueuesResult lq_result = sqs.listQueues();
        System.out.println("Your SQS Queue URLs:");

        List<String> queueUrls = new ArrayList<>();

        for (String url : lq_result.getQueueUrls()) {
            System.out.println(url);
            queueUrls.add(url);
        }

        return queueUrls;
    }

    // List queues with filters
    public static List<String> getUrlByPrefix(String prefix){
        ListQueuesResult lq_result = sqs.listQueues();
        lq_result = sqs.listQueues(new ListQueuesRequest(prefix));

        System.out.println("Queue URLs with prefix: " + prefix);

        List<String> queueUrls = new ArrayList<>();

        for (String url : lq_result.getQueueUrls()) {
            System.out.println(url);
            queueUrls.add(url);
        }

        return queueUrls;
    }

    // send messages to queue
    public static void sendMessage(String queueName, String message, String groupId){
        String queueUrl = sqs.getQueueUrl(queueName).getQueueUrl();

        SendMessageRequest sendMessageRequest = new SendMessageRequest()
                .withQueueUrl(queueUrl)
                .withMessageBody(message)
                .withDelaySeconds(0);

        if(queueName.endsWith(".fifo")){
            sendMessageRequest.withMessageGroupId(groupId);
        }

        try{
            sqs.sendMessage(sendMessageRequest);
        }catch(AmazonSQSException e){
            System.err.println(e.getErrorMessage());
        }

        String sendMessage = sendMessageRequest.getMessageBody();

        System.out.format("sent message which is %s\n", sendMessage);
    }

    // receive messages from queue
    public static List<Message> receiveMessage(String queueName){
        String queueUrl = sqs.getQueueUrl(queueName).getQueueUrl();

        ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest()
            .withQueueUrl(queueUrl)
            .withMaxNumberOfMessages(1)
            .withWaitTimeSeconds(5);
        
        if(queueName.endsWith(".fifo")){
            receiveMessageRequest.withAttributeNames("MessageGroupId");
        }
        
        List<Message> messages = sqs.receiveMessage(receiveMessageRequest).getMessages();


        if(messages.size() == 0){
            System.err.format("%s is empty.\n", queueName);
            System.exit(1);
        }
        
        messages.stream().forEach((msg) -> {
            System.out.format("----------------------------------%s----------------------------------\n", msg.getMessageId());
            System.out.format("id: %s\n", msg.getMessageId());
            System.out.format("body: %s\n", msg.getBody());
            System.out.format("receiptHandle: %s\n", msg.getReceiptHandle());

            sqs.deleteMessage(queueUrl, msg.getReceiptHandle());
            System.out.format("delete %s from queue.\n", msg.getMessageId());
        });
 
        return messages;
    }

}
