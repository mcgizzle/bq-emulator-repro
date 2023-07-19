FROM maven:3.6.3-jdk-11-slim AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn package

FROM eclipse-temurin:11
WORKDIR /app
COPY --from=build /app/target/minimal-1.jar /app/minimal.jar

CMD ["java", "-jar", "/app/minimal.jar"]
