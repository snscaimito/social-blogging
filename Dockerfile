FROM node:18

WORKDIR /app
COPY package*.json ./
RUN npm ci --only=production
RUN npm rebuild sqlite3
COPY . .
EXPOSE 3000
CMD [ "node", "src/server.js" ]
