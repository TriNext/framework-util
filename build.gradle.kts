@file:Suppress("HardCodedStringLiteral")

import org.owasp.dependencycheck.gradle.extension.DependencyCheckExtension

group = "de.trinext"
version = "0.0.6"


java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

plugins {
    `java-library`
    `maven-publish`
    id("java")
    jacoco
    id("org.owasp.dependencycheck") version "8.4.0"
    application
}

application {
    applicationDefaultJvmArgs = listOf("--add-opens", "java.base/java.lang=ALL-UNNAMED")
}

repositories {
    mavenCentral()
}

dependencies {
    // https://mvnrepository.com/artifact/org.mockito/mockito-core
    testImplementation("org.mockito:mockito-core:5.6.0")

    // https://mvnrepository.com/artifact/org.junit.jupiter/junit-jupiter-api
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
    // https://mvnrepository.com/artifact/org.mockito/mockito-core
    testImplementation("org.mockito:mockito-core:5.7.0")


}

// Die folgende Sektion veröffentlicht  das Projekt zur GitHub registry
// damit die folgende Sektion funktioniert, müssen die folgenden Umgebungsvariablen gesetzt korrekt sein:
// TONYS_GITHUB_USERNAME
// TONYS_PAT

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/trinext/framework-util")
            credentials {
                username = project.findProperty("gpr.user") as String? ?: System.getenv("TONYS_GITHUB_USERNAME")
                password = project.findProperty("gpr.key") as String? ?: System.getenv("TONYS_PAT")
            }
        }
    }
    publications {
        register<MavenPublication>("gpr") {
            from(components["java"])
        }
    }
}



tasks.check {
    dependsOn(":dependencyCheckAnalyze")
}

configure<DependencyCheckExtension> {
    format = org.owasp.dependencycheck.reporting.ReportGenerator.Format.ALL.toString()
    analyzers.knownExploitedURL = "https://raw.githubusercontent.com/TriNext/cisa-known-exploited-mirror/main/known_exploited_vulnerabilities.json"
    failBuildOnCVSS = 10.0F
}

tasks.test {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport) // report is always generated after tests run
    jvmArgs = listOf("--add-opens", "java.base/java.lang=ALL-UNNAMED")
}
tasks.jacocoTestReport {
    dependsOn(tasks.test) // tests are required to run before generating the report
}