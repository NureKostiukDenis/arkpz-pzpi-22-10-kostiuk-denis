FROM gradle:8.11-jdk21-corretto AS builder

WORKDIR /app

COPY build.gradle.kts settings.gradle.kts gradlew gradlew.bat ./
COPY gradle ./gradle
COPY src ./src

RUN gradle wrapper
RUN chmod +x gradlew
RUN ./gradlew build

FROM gradle:8.11-jdk21-corretto

WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8002

ENTRYPOINT ["java", "-jar", "app.jar"]
