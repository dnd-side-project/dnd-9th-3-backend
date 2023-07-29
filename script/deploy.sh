#!/bin/bash

IS_GREEN=$(docker ps | grep green) # 현재 실행중인 App이 blue인지 확인한다.
DEFAULT_CONF=" /etc/nginx/nginx.conf"

if [ $(docker ps | grep -c "redis") -eq 0 ]; then
  echo "### Starting redis ###"
  docker-compose up -d redis
else
  echo "redis is already running"
fi

if [ -z "$IS_GREEN" ];then # blue라면
  echo "### BLUE => GREEN ###"

  echo "1. get green image"
  docker-compose --env-file ./.env -f ./.docker/docker-compose.yml pull green # green으로 이미지를 내려받는다.

  echo "2. green container up"
  docker-compose --env-file ./.env -f ./.docker/docker-compose.yml up -d green # green 컨테이너 실행

  while [ 1 = 1 ]; do
    echo "3. green health check..."
    sleep 3

    REQUEST=$(curl http://127.0.0.1:8080/health-check) # green으로 request
    if [ -n "$REQUEST" ]; then # 서비스 가능하면 health check 중지
      echo "health check success"
      break;
    fi
  done;

  echo "4. reload nginx"
  sudo cp /etc/nginx/nginx.green.conf /etc/nginx/nginx.conf
  sudo nginx -s reload

  echo "5. blue container down"
  docker-compose -f ./.docker/docker-compose.yml stop blue
else
  echo "### GREEN => BLUE ###"

  echo "1. get blue image"
  docker-compose --env-file ./.env -f ./.docker/docker-compose.yml pull blue

  echo "2. blue container up"
  docker-compose --env-file ./.env -f ./.docker/docker-compose.yml up -d blue

  while [ 1 = 1 ]; do
    echo "3. blue health check..."
    sleep 3

    REQUEST=$(curl http://127.0.0.1:8080/health-check) # blue로 request
    if [ -n "$REQUEST" ]; then # 서비스 가능하면 health check 중지
      echo "health check success"
      break;
    fi
  done;

  echo "4. reload nginx"
  sudo cp /etc/nginx/nginx.blue.conf /etc/nginx/nginx.conf
  sudo nginx -s reload

  echo "5. green container down"
  docker-compose -f ./.docker/docker-compose.yml stop green
fi