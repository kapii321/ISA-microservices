FROM eclipse-temurin:23.0.1-jre

LABEL title ="music-gateway-service"
LABEL authors="Kacper"
LABEL version="1.0.0-SNAPSHOT"

ENV VERSION="1.0.0-SNAPSHOT"
ENV SERVER_PORT=8080

ENV GATEWAY_HOST=localhost:8080
ENV SONG_SERVICE_URL=http://song:8082
ENV PLAYLIST_SERVICE_URL=http://playlist:8081

EXPOSE 8080

COPY target/gateway-service-${VERSION}.jar /opt/music-app/gateway-service.jar

CMD ["java", "-jar", "/opt/music-app/gateway-service.jar"]