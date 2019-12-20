#!/usr/bin/env bash

TAG=$1

./gradlew build

docker build -t philwin/market_data_loader:${TAG} .
docker build -t philwin/market_data_loader:latest .

docker push philwin/market_data_loader:${TAG}
docker push philwin/market_data_loader:latest