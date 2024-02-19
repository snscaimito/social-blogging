FROM node:18

RUN apt-get update && apt-get install -y sqlite3 && rm -rf /var/lib/apt/lists/*

WORKDIR /app
COPY package*.json ./
RUN npm ci --only=production
RUN npm rebuild sqlite3
COPY . .
EXPOSE 3000
CMD [ "node", "src/server.js" ]
