# Use an official OpenJDK 21 runtime as a parent image
FROM openjdk:21-jdk-slim as builder

# Set the working directory
WORKDIR /app

# Copy the Spring Boot jar from your local machine to the Docker container
COPY target/*.jar app.jar

# Expose the port the app will run on
EXPOSE 8080

# Set the default command to run the Spring Boot app
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
