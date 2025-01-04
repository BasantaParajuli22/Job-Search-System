FROM openjdk:21-jdk-slim

# Set working directory
WORKDIR /app

# Copy gradle wrapper and project files
COPY gradlew gradlew.bat /app/
COPY gradle /app/gradle
COPY build.gradle /app/
COPY settings.gradle /app/
COPY src /app/src

# Make the gradle wrapper executable
RUN chmod +x gradlew

# Build the project using gradle wrapper, skipping tests
RUN ./gradlew build -x test

# Set the entry point for the application
CMD ["java", "-jar", "build/libs/JobSearchProject-0.0.1-SNAPSHOT.jar"]