package com.aws.assignment;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import static java.lang.System.out;

public class S3Uploader {
    private static final String accountId = System.getenv("AWS_ACCOUNT_ID");
    private static final String region = System.getenv("AWS_REGION");
    private static final String app = System.getenv("APP");
    private static final String env = System.getenv("ENV");

    public static void main(String[] args) throws IOException {
        String bucketName = "aws-s3-bucket-simple-backend-assignment-dev";
//        String bucketName = "aws-s3-bucket-" + app + "-" + env;
//	    String prefix = "cars/";
//	    String keyName = prefix + "vehicles.txt";
	    String keyName = "vehicles.txt";
        String resourcePath = "/vehicles.txt";
        AmazonS3 s3 = AmazonS3ClientBuilder.defaultClient();
        URL resourceUrl = S3Uploader.class.getResource(resourcePath);
        if (resourceUrl == null) {
            throw new IllegalArgumentException("Resource not found: " + resourcePath);
        }
        File file = new File(resourceUrl.getFile());
        PutObjectRequest request = new PutObjectRequest(bucketName, keyName, file);

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType("text/plain");
        request.setMetadata(metadata);
        s3.putObject(request);
        out.println("File uploaded to S3 bucket: " + bucketName);
    }
}
