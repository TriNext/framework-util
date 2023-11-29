@file:Suppress("HardCodedStringLiteral")

import org.owasp.dependencycheck.gradle.extension.DependencyCheckExtension

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

plugins {
    id("java")
    jacoco
    id("org.owasp.dependencycheck") version "8.4.0"
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

    // TODO Libraries / Framework-Artefakte können diesen Code-Teil entfernen.
    /*
    implementation("com.sparkjava:spark-core:2.9.4")
    */
}
/*

// Wenn das Projekt eine ausführbare Datei sein soll, kann es mit der folgenden Sektion zu einer FAT-Jar verpackt werden.
// TODO Libraries / Framework-Artefakte können diesen Code-Teil entfernen.

var pathToMain = "de.trinext.app.Main"

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = pathToMain
    }
}

val fatJar = task("fatJar", type = Jar::class) {
    val baseName = "${project.name}-fat"
    archiveFileName.set(baseName + ".jar")
    manifest {
        attributes["Implementation-Title"] = "Gradle Jar File Example"
        attributes["Implementation-Version"] = version
        attributes["Main-Class"] = pathToMain
    }
    from(configurations.runtimeClasspath.get().map({ if (it.isDirectory) it else zipTree(it) }))
    with(tasks.jar.get() as CopySpec)
}
tasks {
    "build" {
        dependsOn(fatJar)
    }
}
tasks.withType<Jar> {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}
 */

//TODO Applikationen können den folgenden Code-Teil entfernen.
// Die folgende Sektion veröffentlicht  das Projekt zur GitHub registry
// damit die folgende Sektion funktioniert, müssen die folgenden Umgebungsvariablen gesetzt korrekt sein:
// TONYS_GITHUB_USERNAME
// TONYS_PAT
/*
publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/trinext/ //TODO hier der name des repositories eintragen werden
             ")
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
*/

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
}
tasks.jacocoTestReport {
    dependsOn(tasks.test) // tests are required to run before generating the report
}
