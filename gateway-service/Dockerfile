FROM eclipse-temurin:23.0.1_11-jre

LABEL org.opencontainers.image.title ="gateway-service"
LABEL org.opencontainers.image.version="0.0.1-SNAPSHOT"

ENV VERSION="0.0.1-SNAPSHOT"
ENV SERVER_PORT=8080

EXPOSE 8080

COPY target/gateway-service-${VERSION}.jar /opt/music-app/gateway-service.jar

CMD ["java", "-jar", "/opt/music-app/gateway-service.jar"]