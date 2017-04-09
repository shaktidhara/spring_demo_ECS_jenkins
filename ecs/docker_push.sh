#!/bin/bash

IMAGE=$1
REPO=$2
TAG=$3
REGION=${4:-"us-east-1"}

docker tag $IMAGE:$TAG $REPO:$TAG
eval `aws ecr get-login --region $REGION`
docker push $REPO:$TAG

