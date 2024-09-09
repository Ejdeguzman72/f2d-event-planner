# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the jar file from your host machine to the working directory in the container
COPY target/event-planner-0.0.1-SNAPSHOT.jar /app/event-planner-0.0.1-SNAPSHOT.jar

# Expose the port that the Spring Boot application will run on
EXPOSE 8082

# Command to run the jar file
ENTRYPOINT ["java", "-jar", "/app/event-planner-0.0.1-SNAPSHOT.jar"]