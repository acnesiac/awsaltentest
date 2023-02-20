#backend "s3" {
#  region = "eu-central-1"
#  bucket = "aws-s3-bucket-${var.APP}-${var.ENV}"
#  key    = "mykey"
#  encrypt = true

#  dynamodb_table = "cars_brands"
#}
