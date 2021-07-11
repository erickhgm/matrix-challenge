FROM maven:3.6.3-jdk-11-slim AS build
RUN mkdir -p /app
WORKDIR /app
COPY pom.xml /app
COPY src /app/src
RUN mvn clean verify

FROM openjdk:11-jdk-slim
COPY --from=build /app/target/*-runner.jar app.jar
EXPOSE 8090
ENTRYPOINT ["java", "-jar", "app.jar"]