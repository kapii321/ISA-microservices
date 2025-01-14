FROM eclipse-temurin:23.0.1_11-jre
LABEL title ="music-playlist-service"
LABEL authors="Kacper"
LABEL version="1.0.0-SNAPSHOT"

ENV VERSION="1.0.0-SNAPSHOT"
ENV SERVER_PORT = 8081


ENV SPRING_DATASOURCE_URL=jdbc:h2:mem:playlistDB
ENV SPRING_DATASOURCE_DRIVERCLASSNAME=org.h2.Driver
ENV SPRING_DATASOURCE_USERNAME=a
ENV SPRING_DATASOURCE_PASSWORD=a

ENV SPRING_JPA_DATABASE_PLATFORM=org.hibernate.dialect.H2Dialect
ENV SPRING_JPA_GENERATE_DDL=true
ENV SPRING_JPA_HIBERNATE_DDL_AUTO=update

ENV SPRING_H2_CONSOLE_enabled=true

COPY target/playlist-service-${VERSION}.jar /opt/music-app/playlist-service.jar

CMD ["java", "-jar", "/opt/music-app/playlist-service.jar"]