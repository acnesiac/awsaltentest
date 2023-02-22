#!/bin/bash
set -e
APP="simple-backend-assignment"

cd infra
terraform init -force-copy && terraform destroy -auto-approve -var-file=$APP.tfvars
#rm -rf .terraform/
