

plugins {
    java
    id("io.spring.dependency-management") version "1.0.15.RELEASE"
    `maven-publish`
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
    implementation("org.springframework:spring-web:6.2.1")
    implementation("org.springframework:spring-webmvc:6.2.1")
    implementation("jakarta.servlet:jakarta.servlet-api:6.1.0")
    testImplementation("org.assertj:assertj-core:3.26.0")
    testImplementation("org.springframework.boot:spring-boot-starter-web:3.4.1")
    testImplementation("org.springframework.boot:spring-boot-starter-test:3.4.1")
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

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])

            // Optional: Customize the generated POM
            pom {
                name.set("remock.spring")
                description.set("A library for generating mock objects on spring")
                url.set("http://www.github.com/mussatto/remock")

                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                developers {
                    developer {
                        id.set("`maven-publish`")
                        name.set("Vitor Mussatto")
                        email.set("mussatto@gmail.com")
                    }
                }
            }
        }
    }

    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/mussatto/remock")
            credentials {
                username = project.findProperty("gpr.user") as String? ?: System.getenv("GH_USERNAME")
                password = project.findProperty("gpr.token") as String? ?: System.getenv("GH_TOKEN")
            }
        }
    }
}