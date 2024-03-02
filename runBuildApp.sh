#!/bin/bash
./mvnw clean package
if [ $? -eq 0 ]; then
  export RSS_FEED=https://www.stephan-schwab.com/rss.xml
  export ATPROTO_ENABLED=false
  docker compose up --build --remove-orphans --always-recreate-deps
fi
