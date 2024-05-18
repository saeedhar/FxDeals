# Use a specific tag for the base image
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the JAR file to the working directory
COPY target/clustered.data.warehouse-0.0.1-SNAPSHOT.jar app.jar

# Expose the port the application runs on
EXPOSE 8080

# Define the entry point for the container
ENTRYPOINT ["java","-jar","app.jar"]


