FROM openjdk:17-jdk-slim

ARG JAR_FILE=build/libs/*.jar

COPY ${JAR_FILE} app.jar

EXPOSE 80

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "/app.jar"]