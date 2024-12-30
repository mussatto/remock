plugins {
    id("java")
}

group = "com.remock"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":spring"))
    implementation("org.springframework.boot:spring-boot-starter-web:3.4.1")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}