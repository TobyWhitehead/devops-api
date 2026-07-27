#!/bin/bash

set -e

echo "Pulling latest image..."

docker compose pull

echo "Stopping old containers..."

docker compose down

echo "Starting new containers..."

docker compose up -d

echo "Checking health..."

curl --fail http://localhost:8080/actuator/health

echo "Deployment successful!"