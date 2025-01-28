FROM eclipse-temurin:23.0.1_11-jre

LABEL org.opencontainers.image.title="discovery-service"
LABEL org.opencontainers.image.version="0.0.1-SNAPSHOT"

ENV VERSION="0.0.1-SNAPSHOT"
ENV SERVER_PORT=8761

EXPOSE 8761

COPY target/discovery-service-${VERSION}.jar /opt/music-app/discovery-service.jar

CMD ["java", "-jar", "/opt/music-app/discovery-service.jar"]