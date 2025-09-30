# ----------------------------------------
# Stage 1: BUILDER
# 💡 FIX: Use a Maven image which includes the JDK and mvn tool.
# ----------------------------------------
FROM maven:3.9.5-eclipse-temurin-21 as builder
WORKDIR /app

# Copy the build file (pom.xml for Maven)
COPY pom.xml .

# Download dependencies first. This step is cached, speeding up rebuilds.
RUN --mount=type=cache,target=/root/.m2 mvn dependency:go-offline

# Copy all source code
COPY src ./src

# Compile the project and package it into a JAR file (in the target/ directory)
RUN mvn clean package -DskipTests

# ----------------------------------------
# Stage 2: RUNTIME (NO CHANGE NEEDED HERE)
# ----------------------------------------
FROM eclipse-temurin:21-jre
WORKDIR /app

# COPY THE JAR: Copy the compiled JAR from the 'builder' stage
COPY --from=builder /app/target/myMonyManager-0.0.1-SNAPSHOT.jar moneymanager-v1.0.jar

# Expose the application port
EXPOSE 9090

# Command to run the application
ENTRYPOINT ["java","-jar","moneymanager-v1.0.jar"]