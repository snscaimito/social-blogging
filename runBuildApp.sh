#!/bin/bash
./mvnw clean package
docker compose up --build --remove-orphans --always-recreate-deps
