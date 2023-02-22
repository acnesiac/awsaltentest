# aws-simple-back-end-assignment for Alten

Adolfo Ruiz Rivas acnesiac@hotmail.com - Alten 

1. To generate the jar files asociated to the lambda function
`mvn clean install`
2. run terraform/destroy.sh  and terraform/create.sh scripts to launch terraform and execute the commands plan & apply 
and saves the plan to out.terraform
4. Login into AWS console to check the following resources :
        S3
        - aws-s3-bucket-simple-backend-assignment-dev	EU (Frankfurt) eu-central-1
        Lambda 
        - my-lambda-function-save-dynamo-simple-backend-assignment-dev
        - my-lambda-function-parse-each-line-simple-backend-assignment-dev
        SQS
        - sqs-simple-backend-assignment-dev.fifo
        SNS topic
        - sns_topic-simple-backend-assignment-dev.fifo
        DynamoDB
        - cars_brands-simple-backend-assignment-dev
4. Run [S3Uploader.java](src%2Fmain%2Fjava%2Fcom%2Faws%2Fassignment%2FS3Uploader.java) to upload a sample file called vehicles.txt into S3 bucket
5. Run [TestS3ToSQS.java](src%2Ftest%2FTestS3ToSQS.java) for testing verification




