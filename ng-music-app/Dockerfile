
FROM nginx:1.23.3

LABEL org.opencontainers.image.title="ng-service"
LABEL org.opencontainers.image.version="0.0.1-SNAPSHOT"

ENV VERSION ="0.0.1-SNAPSHOT"
ENV API_URL=http://gateway-service:8080

EXPOSE 80
ADD dist/ng-music-app /usr/share/nginx/html/
ADD docker/nginx/nginx.conf /etc/nginx/conf.d/default.conf.template


