# Use an official OpenJDK runtime as a parent image
FROM openjdk:21-jdk

# Set the working directory inside the container
WORKDIR /app

# Copy the Spring Boot jar file into the container at /app
COPY target/*.jar app.jar

# Expose the port that the Spring Boot app runs on
EXPOSE 8084

# Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]

# docker run --network="host" -p 8084:8084 your-image-name
