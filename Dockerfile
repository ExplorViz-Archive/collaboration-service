# Use the official Quarkus runtime image
FROM quay.io/quarkus/quarkus-jvm-image:11

# Set the working directory in the container
WORKDIR /app

# Copy the built jar file into the container
COPY ./build/collaboration-service-*-runner.jar /app/app.jar

# Expose the application port
EXPOSE 4444

# Command to run the application
CMD ["java", "-jar", "/app/app.jar"]

