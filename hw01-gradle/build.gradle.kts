plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.guava)

    testImplementation(platform(libs.junit.bom))
    testImplementation(libs.junit.jupiter)
    testRuntimeOnly(libs.junit.platform.launcher)
}

tasks.test {
    useJUnitPlatform()
}

tasks.register<org.gradle.jvm.tasks.Jar>("fatJar") {
    archiveClassifier = "fat"
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE

    dependsOn(tasks.named("compileJava"))

    from(sourceSets["main"].output)
    from(configurations["runtimeClasspath"].map {
        if (it.isDirectory) it else zipTree(it)
    })

    manifest {
        attributes["Main-Class"] = "org.example.Main"
    }
}