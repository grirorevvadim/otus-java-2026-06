import org.gradle.jvm.tasks.Jar

plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:6.0.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}

tasks.register<Jar>("fatJar") {
    archiveClassifier = "fat"
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE

    // include subproject compiled classes and their runtime deps
    subprojects.forEach { subproject ->
        from(subproject.sourceSets["main"].output)
        from(subproject.configurations["runtimeClasspath"].map {
            if (it.isDirectory) it else zipTree(it)
        })
    }

    manifest {
        attributes["Main-Class"] = "src.main.java.org.example.Main"
    }
}