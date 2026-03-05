# Compilación (Build) para entorno linux
FROM gradle:8-jdk21 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN ./gradlew :server:shadowJar --no-daemon

# Ejecución (Runtime)
FROM eclipse-temurin:21-jre-jammy
EXPOSE 8080
WORKDIR /app
COPY --from=build /home/gradle/src/server/build/libs/*-all.jar /app/inventapi.jar
ENTRYPOINT ["java", "-jar", "/app/inventapi.jar"]