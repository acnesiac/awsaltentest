package com.aws.assignment;

import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.amazonaws.services.lambda.runtime.events.SQSEvent.SQSMessage;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;

public class SQSProcessor implements RequestHandler<SQSEvent, Void> {

    private static final String TABLE_NAME = "MyTable";
    private static final String MESSAGE_ID = "MessageId";
    private static final String MESSAGE_BODY = "MessageBody";

    private final AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
    private final DynamoDB dynamoDB = new DynamoDB(client);
    private final String accountId = System.getenv("AWS_ACCOUNT_ID");
    private final String region = System.getenv("AWS_REGION");
    private final String app = System.getenv("APP");
    private final String env = System.getenv("ENV");

    @Override
    public Void handleRequest(SQSEvent event, Context context) {
        for (SQSMessage message : event.getRecords()) {
            // Retrieve the message ID and body
            String messageId = message.getMessageId();
            String messageBody = message.getBody();
            
            // Create an item with the message ID and body
            Item item = new Item()
                    .withPrimaryKey("id", messageId)
                    .withString("brand", messageBody);

            // Save the item in the DynamoDB table
//            Table table = dynamoDB.getTable("cars_brands");
//            Table table = dynamoDB.getTable("cars_brands-simple-backend-assignment-dev");
            Table table = dynamoDB.getTable("cars_brands-" + app + "-" + env);
            table.putItem(item);
        }
        
        return null;
    }
}
