#!/bin/bash

IMAGE_NAME=$1
TAG=$2
REPO_URL=$3
REGION=${4:-"us-east-1"}

eval `aws ecr get-login --region $REGION`
docker push $REPO_URL/$IMAGE_NAME:$TAG

