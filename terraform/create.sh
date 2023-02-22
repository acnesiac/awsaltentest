#!/bin/bash
terraform init -force-copy && terraform plan -out out.terraform && terraform apply -auto-approve out.terraform
