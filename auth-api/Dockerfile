FROM node:16.13.1
WORKDIR .
COPY package*.json ./
RUN yarn
COPY . .
EXPOSE 8080
CMD ["node", "app.js"]
