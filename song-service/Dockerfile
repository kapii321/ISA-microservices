FROM eclipse-temurin:23.0.1-jre

LABEL title ="music-song-service"
LABEL authors="Kacper"
LABEL version="1.0.0-SNAPSHOT"

ENV VERSION="1.0.0-SNAPSHOT"
ENV SERVER_PORT=8082

ENV spring.datasource.url=jdbc:h2:mem:songDB
ENV spring.datasource.driverClassName=org.h2.Driver
ENV spring.datasource.username=a
ENV spring.datasource.password=a

ENV spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
ENV spring.jpa.generate-ddl=true
ENV spring.jpa.hibernate.ddl-auto=update


COPY target/song-service-${VERSION}.jar /opt/music-app/song-service.jar

CMD ["java", "-jar", "/opt/music-app/song-service.jar"]