#!/bin/bash
set -e

export AWS_REGION="eu-central-1"
export AWS_ACCOUNT_ID="633691364715"
export APP="simple-backend-assignment"
export ENV="dev"
#export TF_VAR_aws_region="$AWS_REGION"
#export TF_VAR_aws_account_id="$AWS_ACCOUNT_ID"
#export TF_VAR_app="$APP"
#export TF_VAR_env="$ENV"

BUCKET_STATE="aws-s3-bucket-state-simple-backend-assignment"
KEY="terraform.tfstate"

# Check if the bucket exists
if aws s3 ls --region $AWS_REGION "s3://$BUCKET_STATE" 2>&1 | grep -q 'NoSuchBucket\|AllAccessDisabled'; then
  echo "Bucket does not exist, creating new bucket"
  aws s3 mb "s3://$BUCKET_STATE" --region $AWS_REGION
fi

mvn clean install
mvn dependency:copy-dependencies -DoutputDirectory=lib
cd infra
# Set the backend configuration
cat <<EOF > backend.hcl
bucket = "$BUCKET_STATE"
key    = "terraform.tfstate"
region = "$AWS_REGION"
EOF

cat <<EOF > $APP.tfvars
AWS_REGION = "$AWS_REGION"
AWS_ACCOUNT_ID = "$AWS_ACCOUNT_ID"
APP = "$APP"
ENV = "$ENV"
EOF

# Initialize Terraform for the correct workspace
#terraform init -force-copy -backend-config="bucket=$BUCKET_STATE" -backend-config="key=$KEY" -backend-config="region=$AWS_REGION"
terraform init -force-copy -backend-config=backend.hcl -var backend_bucket_name=$BUCKET_STATE -var backend_bucket_key=$KEY -var backend_bucket_region=$AWS_REGION

# Apply the configuration
#terraform plan -var="aws_region=$AWS_REGION" -var="aws_account_id=$AWS_ACCOUNT_ID" -var="app=$APP" -var="env=$ENV" -out out.terraform && terraform apply -auto-approve out.terraform
terraform plan -var-file=$APP.tfvars -out out.terraform && terraform apply -auto-approve out.terraform

export BUCKET=$(aws s3 ls --region $AWS_REGION | grep aws-s3-bucket-$APP | tail -n1 |cut -d ' ' -f3)
#. ~/.bash_profile
echo "Bucket name: $BUCKET"

# Build the project and run the Java program
cd ..
AWS_JAR_FILE="target/$APP-1.0-SNAPSHOT-aws.jar"
JAR_FILE="target/$APP-1.0-SNAPSHOT.jar"
java -jar "$AWS_JAR_FILE" $BUCKET

#or
#java -cp "target/*:lib/*" com.aws.assignment.S3Uploader $BUCKET


