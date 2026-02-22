# Build
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:resolve
COPY src src
RUN mvn package -DskipTests -q

# Run
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/tpg5-adoo-1.0.jar app.jar
CMD ["java", "-jar", "app.jar"]
