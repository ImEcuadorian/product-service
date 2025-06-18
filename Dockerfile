FROM gradle:8.14.2-jdk21-alpine AS builder

WORKDIR /app

COPY build.gradle.kts settings.gradle.kts gradlew ./
COPY gradle ./gradle
RUN ./gradlew dependencies --no-daemon

COPY . .

RUN ./gradlew clean bootJar --no-daemon

FROM eclipse-temurin:21-jre-alpine

LABEL org.opencontainers.image.title="product-service" \
      org.opencontainers.image.version="1.0.0" \
      org.opencontainers.image.description="Spring Boot reactive product microservice" \
      org.opencontainers.image.source="https://github.com/imecuadorian/product-service"

WORKDIR /app

COPY --from=builder /app/build/libs/product-service-0.0.1-SNAPSHOT.jar app.jar

ENV SPRING_PROFILES_ACTIVE=docker
EXPOSE 8080

HEALTHCHECK --interval=30s --timeout=10s --start-period=20s \
  CMD wget --no-verbose --tries=1 --spider http://localhost:8080/actuator/health || exit 1

ENTRYPOINT ["java", "-jar", "app.jar"]
