data "archive_file" "lambda_function_01" {
  type        = "zip"
  source_file = "${path.module}/../target/simple-backend-assignment-1.0-SNAPSHOT-aws.jar"
  output_path= "${path.module}/../target/simple-backend-assignment-1.0-SNAPSHOT-aws.zip"
}

resource "aws_lambda_function" "my_lambda_function_01" {
#  filename         = data.archive_file.lambda_function_01.output_path
  filename         = "${path.module}/../target/simple-backend-assignment-1.0-SNAPSHOT-aws.jar"
  function_name    = "my-lambda-function-parse-each-line-${var.APP}-${var.ENV}"
  handler          = "com.aws.assignment.S3ToSQS::handleRequest"
  runtime          = "java8"
  role             = aws_iam_role.my_lambda_role.arn
#  role             =  aws_iam_role.lambda_iam.arn
  timeout          = 10
  memory_size      = 256
  #source_code_hash = data.archive_file.lambda_function.output_base64sha256
  source_code_hash = filebase64sha256(data.archive_file.lambda_function_01.output_path)

  environment {
    variables = {
      "APP" = var.APP,
      "ENV" = var.ENV,
#      "AWS_REGION" = var.AWS_REGION,
      "AWS_ACCOUNT_ID" = var.AWS_ACCOUNT_ID
      QUEUE_URL = aws_sqs_queue.my_sqs.url
    }
  }

  lifecycle {
    create_before_destroy = true
  }
}

/*
data "aws_iam_policy_document" "s3_invoke_lambda_policy" {
  statement {
    actions = ["lambda:InvokeFunction"]
    effect  = "Allow"
    resources = [
      aws_lambda_function.my_lambda_function_01.arn,
    ]
    principals {
      type = "Service"
      identifiers = ["s3.amazonaws.com"]
    }
  }
  depends_on = [aws_s3_bucket.my_bucket]
}
*/

resource "aws_lambda_permission" "test" {
  statement_id  = "AllowS3Invoke"
  action        = "lambda:InvokeFunction"
  function_name = aws_lambda_function.my_lambda_function_01.function_name
  principal     = "s3.amazonaws.com"
  source_arn    = "arn:aws:s3:::${aws_s3_bucket.my_bucket.id}"
}
