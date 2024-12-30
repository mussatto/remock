plugins {
    java
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
    implementation("com.fasterxml.jackson.core:jackson-databind:2.17.1")
    testImplementation("org.assertj:assertj-core:3.26.0")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.10.2")
//    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
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
                name.set("remock.core")
                description.set("A library for generating mock objects")
                url.set("http://www.github.com/mussatto/remock")

                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                developers {
                    developer {
                        id.set("mussatto")
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