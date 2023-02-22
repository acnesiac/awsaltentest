resource "aws_s3_bucket" "my_bucket" {
  bucket = "aws-s3-bucket-${var.APP}-${var.ENV}"
  acl    = "private"
  force_destroy = true

  lifecycle {
    create_before_destroy = true
  }

  tags = {
    Name = "${var.APP}-${var.ENV}"
  }
}

resource "aws_s3_bucket_versioning" "my_bucket_versioning" {
  bucket = aws_s3_bucket.my_bucket.id

  versioning_configuration {
    status = "Enabled"
  }
}

resource "aws_s3_bucket_server_side_encryption_configuration" "my_bucket_sse_config" {
  bucket = aws_s3_bucket.my_bucket.id

  rule {
    apply_server_side_encryption_by_default {
      sse_algorithm = "AES256"
    }
  }
}

resource "aws_s3_bucket_lifecycle_configuration" "my_bucket_lifecycle" {
  bucket = aws_s3_bucket.my_bucket.id
  rule {
    id      = "delete-after-30-days"
    status  = "Enabled"
    filter {
      prefix = ""
    }
    expiration {
      days = 30
    }
  }
}


resource "aws_s3_bucket_notification" "my_bucket_notification" {
  bucket = aws_s3_bucket.my_bucket.id

  lambda_function {
    lambda_function_arn = aws_lambda_function.my_lambda_function_01.arn
    events = ["s3:ObjectCreated:*"]
#    filter_prefix = "cars/"
#    filter_suffix = "vehicles.txt"
  }
}
