#!/bin/bash

SERVICE_NAME=$1
APP_VERSION=$2
TASK_FAMILY=$3
LOAD_BALANCER_NAME=$4
TARGET_GROUP_ARN=$5
CONTAINER_PORT=$6
ECS_SERVICE_ROLE=$7
GAME=$8
ENVIRONMENT=$9
CLUSTER=${10:-default}

echo "SERVICE_NAME=$SERVICE_NAME"
echo "APP_VERSION=$APP_VERSION"
echo "TASK_FAMILY=$TASK_FAMILY"
echo "LOAD_BALANCER_NAME=$LOAD_BALANCER_NAME"
echo "TARGET_GROUP_ARN=$TARGET_GROUP_ARN"
echo "CONTAINER_PORT=$CONTAINER_PORT"
echo "ECS_SERVICE_ROLE=$ECS_SERVICE_ROLE"
echo "GAME=$GAME"
echo "ENVIRONMENT=$ENVIRONMENT"
echo "CLUSTER=$CLUSTER"

echo "Deploying build number $APP_VERSION for service '$SERVICE_NAME'"

# Create a new task definition for this build
sed -e "s/%APP_VERSION%/${APP_VERSION}/g" ecs/$ENVIRONMENT/$GAME/task-definition.json > $TASK_FAMILY-${APP_VERSION}.json

aws ecs register-task-definition \
  --family $TASK_FAMILY \
  --region 'us-east-1' \
  --cli-input-json file://$TASK_FAMILY-${APP_VERSION}.json

# Create the service if it doesn't already exist
DESCRIBE_JSON=`aws ecs describe-services --region 'us-east-1' --cluster $CLUSTER --services $SERVICE_NAME`
if [ ! `echo $DESCRIBE_JSON | jq .services[0].status | grep -w "ACTIVE"` ] || echo $DESCRIBE_JSON | jq -e .failures[0]; then
  echo "Service '${SERVICE_NAME}' does not exist -- creating it."
  aws ecs create-service \
    --cluster $CLUSTER \
    --region 'us-east-1' \
    --service-name $SERVICE_NAME \
    --task-definition $TASK_FAMILY \
    --load-balancers targetGroupArn=$TARGET_GROUP_ARN,loadBalancerName=$LOAD_BALANCER_NAME,containerName=$TASK_FAMILY,containerPort=$CONTAINER_PORT \
    --role $ECS_SERVICE_ROLE \
    --desired-count 0
fi

# Update the service with the new task definition and desired count
TASK_REVISION=`aws ecs describe-task-definition --task-definition $TASK_FAMILY --region 'us-east-1' | jq .taskDefinition.revision`
DESIRED_COUNT=`aws ecs describe-services --cluster $CLUSTER --services ${SERVICE_NAME} --region 'us-east-1' | jq .services[0].desiredCount`

if [ ${DESIRED_COUNT} = "0" ]; then
    DESIRED_COUNT="2"
fi

# Push the new task to the service itself
aws ecs update-service \
  --cluster $CLUSTER \
  --service ${SERVICE_NAME} \
  --task-definition ${TASK_FAMILY}:${TASK_REVISION} \
  --desired-count ${DESIRED_COUNT} \
  --region 'us-east-1'

