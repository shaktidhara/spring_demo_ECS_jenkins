#!/bin/bash

SERVICE_NAME=$1
BUILD_NUMBER=$2
TASK_FAMILY=$3
LOAD_BALANCER_NAME=$4
CONTAINER_PORT=$5
ECS_SERVICE_ROLE=$6
CLUSTER=${7:-default}
IMAGE_VERSION="v_"${BUILD_NUMBER}

echo "Deploying build number $BUILD_NUMBER for service $SERVICE_NAME"

# Create a new task definition for this build
sed -e "s;%BUILD_NUMBER%;${BUILD_NUMBER};g" ecs/$TASK_FAMILY.json > $TASK_FAMILY-v_${BUILD_NUMBER}.json
aws ecs register-task-definition --family $TASK_FAMILY --region 'us-east-1' --cli-input-json file://$TASK_FAMILY-v_${BUILD_NUMBER}.json

if [[ ! `aws ecs describe-services --region 'us-east-1' --services $SERVICE_NAME | jq .failures[0]` ]]; then
  aws ecs create-service --cluster $CLUSTER --region 'us-east-1' --service-name $SERVICE_NAME --task-definition $TASK_FAMILY --load-balancers loadBalancerName=$LOAD_BALANCER_NAME,containerName=$TASK_FAMILY,containerPort=$CONTAINER_PORT --role $ECS_SERVICE_ROLE --desired-count 0
fi

# Update the service with the new task definition and desired count
TASK_REVISION=`aws ecs describe-task-definition --task-definition $TASK_FAMILY --region 'us-east-1' | jq .taskDefinition.revision`
DESIRED_COUNT=`aws ecs describe-services --services ${SERVICE_NAME} --region 'us-east-1' | jq .services[0].desiredCount`

echo "!!!! - DESIRED_COUNT = $DESIRED_COUNT" # delete me

if [ ${DESIRED_COUNT} = "0" ]; then
    DESIRED_COUNT="1"
fi

aws ecs update-service --cluster $CLUSTER --service ${SERVICE_NAME} --task-definition ${TASK_FAMILY}:${TASK_REVISION} --desired-count ${DESIRED_COUNT} --region 'us-east-1'
