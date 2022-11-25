package aws.practice.dynamoDB;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.IntStream;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.AttributeAction;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.AttributeValueUpdate;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.CreateTableResult;
import com.amazonaws.services.dynamodbv2.model.GetItemRequest;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;
import com.amazonaws.services.dynamodbv2.model.TableDescription;


public class DynamoDBHandler{
    private static final AmazonDynamoDB ddb = AmazonDynamoDBClientBuilder.defaultClient();
    private static final Integer flagNum = 18;

    private DynamoDBHandler(){

    }

    // create table
    public static void createTable(String tableName, String primaryKey){
        System.out.format("Creating table \"%s\" with a simple primary key: \"Name\".\n", tableName);

        if(tableName.contains(" ")) {
            System.out.println("must be not blank!");
            return;
        }
        
        CreateTableRequest request = new CreateTableRequest().withAttributeDefinitions(new AttributeDefinition(primaryKey, ScalarAttributeType.S))
            .withKeySchema(new KeySchemaElement(primaryKey, KeyType.HASH))
            // .withProvisionedThroughput(new ProvisionedThroughput(new Long(10), new Long(10))) for version 8 
            .withProvisionedThroughput(new ProvisionedThroughput((long)10, (long)10))
            .withTableName(tableName);

        try {
            CreateTableResult result = ddb.createTable(request);
            System.out.println(result);
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            System.exit(1);
        }
        System.out.format("created %s.\n", tableName);
    }

    // delete table
    public static void deleteTable(String tableName){
        try {
            ddb.deleteTable(tableName);
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            System.exit(1);
        }

        System.out.format("delete table which is %s.\n", tableName);
    }

    // insert item
    public static void insertItem(String tableName, String targetKey){
        String primaryKey = getKeyName(tableName, KeyType.HASH);

        try {
            IntStream.range(1, flagNum + 1).forEach(i -> {
                HashMap<String,AttributeValue> item_values = new HashMap<String,AttributeValue>();

                String primaryValue = primaryKey + " " + i;

                item_values.put(primaryKey, new AttributeValue(primaryValue));
                item_values.put(targetKey, new AttributeValue("0"));

                ddb.putItem(tableName, item_values);
            });
        } catch (ResourceNotFoundException e) {
            System.err.format("Error: The table \"%s\" can't be found.\n", tableName);
            System.err.println("Be sure that it exists and that you've typed its name correctly!");
            System.exit(1);
        } catch (AmazonServiceException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
        System.out.println("Done!");
    }

    // get flag item
    public static String getItem(String tableName, String targetPrimaryKey, String targetKey){
        String flag = null;
        String primaryKey = getKeyName(tableName, KeyType.HASH);
        HashMap<String,AttributeValue> item_values = new HashMap<String,AttributeValue>();
        item_values.put(primaryKey, new AttributeValue(targetPrimaryKey));
        GetItemRequest request = new GetItemRequest().withKey(item_values).withTableName(tableName);

        try {
            Map<String,AttributeValue> returned_item = ddb.getItem(request).getItem();

            if (returned_item != null) {
                AttributeValue attributeValue = returned_item.get(targetKey);
                System.out.println(attributeValue.getS());
                flag = attributeValue.getS();
            } else {
                System.out.format("No item found with the key %s!\n", targetPrimaryKey);
                throw new AmazonServiceException("flag is null.");
            }

        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            System.exit(1);
        }

        return flag;

    }

    // update item
    public static void updateItem(String tableName, String targetPrimaryKey, String targetKey, String targetKeyValue){
        String primaryKey = getKeyName(tableName, KeyType.HASH);

        System.out.format("%s's flag was %s.\n", targetPrimaryKey, targetKeyValue);

        HashMap<String,AttributeValue> item_key = new HashMap<String,AttributeValue>();

        item_key.put(primaryKey, new AttributeValue(targetPrimaryKey));

        HashMap<String,AttributeValueUpdate> updated_values = new HashMap<String,AttributeValueUpdate>();

        updated_values.put(targetKey, new AttributeValueUpdate(new AttributeValue(targetKeyValue), AttributeAction.PUT));

        try {
            ddb.updateItem(tableName, item_key, updated_values);
            System.out.format("%s's flag is %s now.\n", targetPrimaryKey, targetKeyValue);
        } catch (ResourceNotFoundException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        } catch (AmazonServiceException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    // get primary key
    private static String getKeyName(String tableName, KeyType keyType){
        TableDescription tableInfo = ddb.describeTable(tableName).getTable();
        String targetKey = null;

        try{
            if(Objects.isNull(tableInfo)) throw new Exception("can not get table info");
        
            List<KeySchemaElement> KeySchemaElements = tableInfo.getKeySchema();

            Optional<String> result = KeySchemaElements.stream()
                .filter(e -> e.getKeyType().equals(keyType.toString()))
                .map(e -> e.getAttributeName()).findAny();

            if(!result.isPresent()) {
                throw new Exception(String.format("result is empty. probably, %s key is not exist.", keyType));
            }

            targetKey = result.get();
        }catch(Exception e){
            System.err.println(e.getMessage());
            System.exit(1);
        }

        System.out.format("found %s key name which is %s.\n", keyType, targetKey);

        return targetKey;
    }
}
