#!/bin/bash

IMAGE_NAME=$1
TAG=$2
REGION=${3:-"us-east-1"}

eval `aws ecr get-login --region $REGION`
docker push $IMAGE_NAME:$TAG

