FROM eclipse-temurin:23.0.1_11-jre

LABEL org.opencontainers.imagetitle ="song-service"
LABEL org.opencontainers.image.version="0.0.1-SNAPSHOT"

ENV VERSION="0.0.1-SNAPSHOT"

ENV SERVER_PORT=8080

ENV SPRING_DATASOURCE_URL=jdbc:h2:mem:songDB
ENV SPRING_DATASOURCE_DRIVERCLASSNAME=org.h2.Driver
ENV SPRING_DATASOURCE_USERNAME=a
ENV SPRING_DATASOURCE_PASSWORD=a

ENV SPRING_JPA_DATABASE_PLATFORM=org.hibernate.dialect.H2Dialect
ENV SPRING_JPA_GENERATE_DDL=true
ENV SPRING_JPA_HIBERNATE_DDL_AUTO-auto=update

ENV SPRING_H2_CONSOLE_enabled=true

EXPOSE 8080

COPY target/song-service-${VERSION}.jar /opt/music-app/song-service.jar

CMD ["java", "-jar", "/opt/music-app/song-service.jar"]