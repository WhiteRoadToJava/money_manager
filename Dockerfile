# ----------------------------------------
# Stage 1: BUILDER
# Uses the full JDK to compile the code and create the JAR file.
# ----------------------------------------
FROM eclipse-temurin:21-jdk as builder
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
# Stage 2: RUNTIME
# Uses the smaller JRE image for the final, lightweight container.
# ----------------------------------------
FROM eclipse-temurin:21-jre
WORKDIR /app

# COPY THE JAR: Copy the compiled JAR from the 'builder' stage
COPY --from=builder /app/target/myMonyManager-0.0.1-SNAPSHOT.jar moneymanager-v1.0.jar

# Expose the application port
EXPOSE 9090

# Command to run the application
ENTRYPOINT ["java","-jar","moneymanager-v1.0.jar"]["java", "-jar", "moneymanager-v1.0.jar"]