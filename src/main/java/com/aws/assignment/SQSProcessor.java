package com.aws.assignment;


import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.amazonaws.services.lambda.runtime.events.SQSEvent.SQSMessage;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;

/**
 * SQSEvent Lambda handler to invoke dynamoDb insert operations after SQSEvent is present
 */
public class SQSProcessor implements RequestHandler<SQSEvent, Void> {
    private final AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
    private final DynamoDB dynamoDB = new DynamoDB(client);
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
            Table table = dynamoDB.getTable("cars_brands-" + app + "-" + env);
            table.putItem(item);
        }
        return null;
    }
}
