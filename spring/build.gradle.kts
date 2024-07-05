

plugins {
    java
    id("io.spring.dependency-management") version "1.0.15.RELEASE"
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

group = "com.remock"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":core"))
    implementation("org.springframework:spring-web:6.1.8")
    implementation("org.springframework.boot:spring-boot-starter-web:3.2.6")
    implementation("org.springframework:spring-webmvc:6.1.8")
    implementation("jakarta.servlet:jakarta.servlet-api:6.1.0")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.2")
    testImplementation("org.assertj:assertj-core:3.26.0")
    testImplementation("org.springframework.boot:spring-boot-starter-test:3.2.6")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.10.2")
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.test {
    testLogging {
        events("PASSED", "FAILED", "SKIPPED")
        showStandardStreams = true
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
