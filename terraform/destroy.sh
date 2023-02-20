#!/bin/bash
terraform init -force-copy
terraform destroy -auto-approve
#rm -rf .terraform/
