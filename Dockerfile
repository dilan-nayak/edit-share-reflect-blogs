# Use Maven to build the application with OpenJDK 17
FROM maven:3.9.6-eclipse-temurin-17 as build
WORKDIR /app

# Copy build descriptor and download dependencies
COPY pom.xml mvnw .
COPY .mvn .mvn
RUN ./mvnw -B dependency:go-offline

# Copy project source and package
COPY src src
RUN ./mvnw -B package -DskipTests

# Runtime image using OpenJDK 17
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
