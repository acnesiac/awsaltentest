package com.aws.assignment;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import static com.aws.assignment.S3Uploader.upload;
import static java.lang.System.out;

/**
 * Start point for the application, reads /vehicles.txt and uploads to the bucket aws-s3-bucket-simple-backend-assignment-dev
 * Its necessary AWS-CLI, /.aws/config and /.aws/credentials files properly configured.
 */
public class S3Uploader {
    public static void main(String[] args) throws IOException {
        upload();
    }

    public static void upload(){
        String bucketName = "aws-s3-bucket-simple-backend-assignment-dev";
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
