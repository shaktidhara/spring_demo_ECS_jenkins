#!/bin/bash

IMAGE=$1
REPO=$2
TAG=$3
REGION=${4:-"us-east-1"}

docker tag $IMAGE:$TAG $REPO:$TAG

# try the login we have before calling ecr get-login to avoid rate limitting

COUNTER=0

while (! docker push $REPO:$TAG) && [ $COUNTER -le 2 ]; do
  eval `aws ecr get-login --region $REGION`
  COUNTER=$((COUNTER+1))
done

