# 🧠 Product Service

Product Service is a reactive microservice built with **Spring WebFlux**, using **TDD**, **BDD**, Docker, and CI/CD with
GitHub Actions.

## 🔧 Tech Stack

- Java 21
- Spring Boot 3.4+
- WebFlux + R2DBC
- PostgreSQL
- MapStruct
- Cucumber (BDD)
- JUnit + Mockito (TDD)
- Spotless + Checkstyle
- Docker / Docker Compose
- GitHub Actions (CI/CD)

## 📦 Getting Started

```bash
./gradlew build
docker compose up
```

## ✅ Run tests

```bash
./gradlew test           # Unit tests
./gradlew cucumberTest   # BDD tests
```

## 🛡️ License

MIT — see [LICENSE](LICENSE)
