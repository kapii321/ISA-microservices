FROM node:18.15 as builder

WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build --configuration=production

# Stage 2: NGINX
FROM nginx:1.23.3
COPY --from=builder /app/dist/ng-music-app /usr/share/nginx/html/
COPY docker/nginx/nginx.conf /etc/nginx/conf.d/default.conf
ENV API_URL=http://localhost:8080
EXPOSE 80
