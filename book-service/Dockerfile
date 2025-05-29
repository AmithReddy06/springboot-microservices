FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/book-service-0.0.1-SNAPSHOT.jar book-service.jar

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "book-service.jar"]
