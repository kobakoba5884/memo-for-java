package aws;

import java.util.stream.IntStream;

import aws.dynamoDB.DynamoDBHandler;
import aws.sqs.SQSHandler;

public class Infra {
    public static void main(String[] args) {
        DynamoDBHandler.createTable("test", "flag name");
        
        String queueName = "queueName";
        IntStream.range(1, 19).forEach((i) -> {
            String name = String.format("%s%d.fifo", queueName, i);
            SQSHandler.createOriginalQueue(name);
        });
    }
    
}
// ---------------------dynamoDB Handler---------------------
        
        // DynamoDBHandler.createTable("test", "flag name");
        // DynamoDBHandler.deleteTable("test");
        // DynamoDBHandler.insertItem("test", "flag");
        // DynamoDBHandler.getItem("test", "flag name 11", "flag");
        // DynamoDBHandler.updateItem("test", "flag name 11", "flag", "1");

    // ---------------------sqs Handler---------------------
        // String queueName = "queueName";
        // IntStream.range(1, 19).forEach((i) -> {
        //     String name = String.format("%s%d.fifo", queueName, i);
        //     SQSHandler.createOriginalQueue(name);
        // });

        // SQSHandler.createOriginalQueue("test");

        // IntStream.range(1, 19).forEach((i) -> {
        //     String name = String.format("%s%d.fifo", queueName, i);
        //     SQSHandler.deleteOriginalQueue(name);
        // });
        // SQSHandler.deleteOriginalQueue("test");

        // SQSHandler.sendMessage("queueName1.fifo", "testtest", "groupId");

        // SQSHandler.receiveMessage("queueName1.fifo");

        // SQSHandler.deleteAllQueue();
