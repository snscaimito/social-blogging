#!/bin/bash
./mvnw clean package
if [ $? -eq 0 ]; then
    docker compose up --build --remove-orphans --always-recreate-deps
fi
