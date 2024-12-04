# Step 1: Use Maven to build the application
FROM maven:3.8.5-eclipse-temurin-17 as builder

# Set working directory inside the container
WORKDIR /app

# Copy the Maven project files into the container
COPY pom.xml ./
COPY src ./src

# Build the project without running tests
RUN mvn clean package -DskipTests

# Step 2: Use a lightweight JDK image to run the application
FROM eclipse-temurin:17-jdk

# Set working directory inside the container
WORKDIR /app

# Copy the built JAR file from the Maven builder stage
COPY --from=builder /app/target/*.jar app.jar

# Expose the application's port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
