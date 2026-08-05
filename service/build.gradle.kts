plugins {
    kotlin("jvm")
    kotlin("plugin.spring")
    kotlin("plugin.jpa")
    id("org.springframework.boot")
    id("io.spring.dependency-management")
}

extra["kotlin.version"] = "2.3.21"

dependencyManagement {
    dependencies {
        dependencySet("org.jetbrains.kotlinx:1.11.0") {
            entry("kotlinx-serialization-core")
            entry("kotlinx-serialization-core-jvm")
            entry("kotlinx-serialization-json")
            entry("kotlinx-serialization-json-jvm")
            entry("kotlinx-serialization-bom")
        }
    }
}

dependencies {
    implementation(project(":dto"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.xerial:sqlite-jdbc:3.46.1.3")
    implementation("org.hibernate.orm:hibernate-community-dialects")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.9.0")

    testImplementation(project(":client"))
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation("org.mockito.kotlin:mockito-kotlin:6.3.0")
}

tasks.test {
    useJUnitPlatform()
}
