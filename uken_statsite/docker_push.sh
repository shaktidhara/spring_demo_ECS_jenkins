#!/bin/bash

IMAGE=uken_website
REPO=661382096004.dkr.ecr.us-east-1.amazonaws.com/uken_website
TAG=$1
REGION=${2:-"us-east-1"}

docker tag $IMAGE:$TAG $REPO:$TAG

# try the login we have before calling ecr get-login to avoid rate limitting

COUNTER=0

while (! docker push $REPO:$TAG) && [ $COUNTER -le 2 ]; do
  eval `aws ecr get-login --region $REGION`
  COUNTER=$((COUNTER+1))
done
