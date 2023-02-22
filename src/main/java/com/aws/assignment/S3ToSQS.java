package com.aws.assignment;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.event.S3EventNotification;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.SendMessageRequest;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import static java.lang.System.*;

/**
 * Lambda class to handle S3 notification events
 */
public class S3ToSQS implements RequestHandler<S3Event, String> {
    private final AmazonS3 s3 = AmazonS3ClientBuilder.standard().build();
    private final AmazonSQS sqs = AmazonSQSClientBuilder.standard().build();
    private final String accountId = System.getenv("AWS_ACCOUNT_ID");
    private final String region = System.getenv("AWS_REGION");
    private final String app = System.getenv("APP");
    private final String env = System.getenv("ENV");

    public String handleRequest(S3Event event, Context context) {
        try {
            S3EventNotification.S3EventNotificationRecord record = event.getRecords().get(0);
            String bucket = record.getS3().getBucket().getName();
            String key = record.getS3().getObject().getKey().replace('+', ' ');
            S3Object s3Object = s3.getObject(new GetObjectRequest(bucket, key));
            InputStream inputStream = s3Object.getObjectContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                // Post the content of each line to the SQS queue
                SendMessageRequest sendMessageRequest = new SendMessageRequest()
                        .withQueueUrl("https://sqs." + region + ".amazonaws.com/" + accountId + "/sqs-" + app + "-" + env +".fifo")
                        .withMessageBody(line)
                        .withMessageGroupId("my-message-group-id")
                        .withMessageDeduplicationId(line);
                sqs.sendMessage(sendMessageRequest);
            }
            reader.close();
            // Delete after successfull
            s3.deleteObject(bucket, key);
            return "Success";
        } catch (Exception e) {
            err.println(e.toString());
            return "Error";
        }
    }
}
