plugins {
    java
    application
    id("org.springframework.boot") version "3.4.7-SNAPSHOT"
    id("io.spring.dependency-management") version "1.1.7"
    id("checkstyle")
    id("jacoco")
}

group = "io.github.imecuadorian"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
    maven { url = uri("https://repo.spring.io/snapshot") }
}

extra["springCloudAzureVersion"] = "5.22.0"
extra["springCloudVersion"] = "2024.0.1"

val jetbrainsAnnotationsVersion = "24.0.1"
val logbackVersion = "1.5.13"
val logstashEncoderVersion = "7.4"
val bouncycastleVersion = "1.80"
val checkstyleVersion = "10.12.1"
val cucumberVersion = "7.11.2"
val junitVersion = "5.10.2"
val junitPlatformVersion = "1.10.2"
val mockitoVersion = "5.12.0"

val mapStructVersion = "1.6.3"
val mapStructBindingVersion = "0.2.0"

val springSwaggerVersion = "2.8.9"
dependencies {
    // Spring Core
    implementation("org.projectlombok:lombok-mapstruct-binding:${mapStructBindingVersion}")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
    implementation("org.springframework.kafka:spring-kafka")
    implementation("com.azure.spring:spring-cloud-azure-starter-keyvault-secrets")
    implementation("org.mapstruct:mapstruct:$mapStructVersion")
    implementation("io.micrometer:micrometer-registry-prometheus")
    implementation("io.zipkin.reporter2:zipkin-reporter-brave")
    implementation("io.micrometer:micrometer-tracing-bridge-brave")
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign")
    implementation("org.springframework.boot:spring-boot-starter-data-redis-reactive")
    implementation("org.springframework.cloud:spring-cloud-starter-circuitbreaker-reactor-resilience4j")
    implementation("ch.qos.logback:logback-classic:${logbackVersion}")
    implementation("net.logstash.logback:logstash-logback-encoder:${logstashEncoderVersion}")
    implementation("org.jetbrains:annotations:${jetbrainsAnnotationsVersion}")
    implementation("org.bouncycastle:bcprov-jdk15to18:${bouncycastleVersion}")
    implementation("org.springdoc:springdoc-openapi-starter-webflux-ui:${springSwaggerVersion}")
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    implementation("net.logstash.logback:logstash-logback-encoder:$logstashEncoderVersion")
    implementation("org.jetbrains:annotations:$jetbrainsAnnotationsVersion")
    implementation("org.bouncycastle:bcprov-jdk15to18:$bouncycastleVersion")
    implementation("org.springdoc:springdoc-openapi-starter-webflux-ui:$springSwaggerVersion")

    runtimeOnly("org.postgresql:r2dbc-postgresql")
    runtimeOnly("org.postgresql:postgresql")

    compileOnly("org.projectlombok:lombok")

    annotationProcessor("org.projectlombok:lombok")
    annotationProcessor("org.mapstruct:mapstruct-processor:$mapStructVersion")


    testImplementation(platform("org.junit:junit-bom:$junitVersion"))
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("org.junit.jupiter:junit-jupiter-params")
    testImplementation("org.junit.platform:junit-platform-suite-api:$junitPlatformVersion")

    testImplementation("io.cucumber:cucumber-java:$cucumberVersion")
    testImplementation("io.cucumber:cucumber-junit-platform-engine:$cucumberVersion")

    testImplementation("org.mockito:mockito-core:$mockitoVersion")

    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage")
    }

    testImplementation("org.springframework.security:spring-security-test")
    testImplementation("org.springframework.kafka:spring-kafka-test")
    testImplementation("io.projectreactor:reactor-test")

    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher:$junitPlatformVersion")

    "developmentOnly"("org.springframework.boot:spring-boot-devtools")
}

dependencyManagement {
    imports {
        mavenBom("com.azure.spring:spring-cloud-azure-dependencies:${property("springCloudAzureVersion")}")
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
    }
}

checkstyle {
    toolVersion = checkstyleVersion
    configFile = file("config/checkstyle/checkstyle.xml")
}

tasks.withType<Test> {
    useJUnitPlatform()
    systemProperty("spring.profiles.active", "test")
}

tasks.bootBuildImage {
    builder = "paketobuildpacks/builder-jammy-base:latest"
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    reports {
        xml.required.set(true)
        html.required.set(true)
    }
}

application {
    mainClass.set("io.github.imecuadorian.product.ProductServiceApplication")
}

jacoco {
    toolVersion = "0.8.10"
    reportsDirectory.set(layout.buildDirectory.dir("jacoco"))
}

tasks.register<Test>("cucumberTest") {
    description = "Runs the Cucumber features using the Cucumber engine"
    group = "verification"

    useJUnitPlatform {
        includeEngines("cucumber")
    }

    testClassesDirs = sourceSets["test"].output.classesDirs
    classpath = sourceSets["test"].runtimeClasspath
}
