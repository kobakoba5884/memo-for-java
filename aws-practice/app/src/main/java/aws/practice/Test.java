package aws.practice;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;

public class Test {
    public static void main(String[] args) {
        AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder.standard().build();
        DynamoDB dynamoDB = new DynamoDB(amazonDynamoDB);

        System.out.println(dynamoDB);

        AmazonSQS amazonSQS = AmazonSQSClientBuilder.standard().withRegion("ap-northeast-1").build();

        System.out.println(amazonSQS);

        dynamoDB.shutdown();

        amazonSQS.shutdown();
    }
}
