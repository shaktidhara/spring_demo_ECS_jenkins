#!/bin/bash

IMAGE=$1
TAG=$2
REPO=$3

docker build -t $IMAGE:$TAG .
docker tag $IMAGE:$TAG $REPO/$IMAGE:$TAG

