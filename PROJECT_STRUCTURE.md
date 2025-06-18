# 🧱 Project Structure

```text
src
├── main
│   ├── java/io/github/imecuadorian/product
│   │   ├── config          # Security config
│   │   ├── controller      # REST endpoints
│   │   ├── dto             # Request/response models
│   │   ├── handler         # Exception handling
│   │   ├── mapper          # MapStruct mappers
│   │   ├── model           # Domain entities
│   │   ├── repository      # R2DBC repositories
│   │   └── service         # Business logic
│   └── resources
│       ├── application.yml
│       ├── application-dev.yml
│       ├── application-docker.yml
│       └── application-prod.yml
└── test
    ├── java/io/github/imecuadorian/product
    │   ├── controller       # TDD
    │   └── bdd              # BDD (Cucumber)
    └── resources/features   # .feature files
```
