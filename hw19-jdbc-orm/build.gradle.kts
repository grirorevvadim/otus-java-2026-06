plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.lombok)
    annotationProcessor(libs.lombok)
    implementation(libs.slf4j.api)
    implementation(libs.logback.classic)
    implementation(libs.flyway.core)
    runtimeOnly(libs.flyway.db.postgresql)

    implementation(libs.postgresql)
    implementation(libs.zaxxer.hikari)
    implementation(libs.xerial.sqline.jdbc)

}

tasks.test {
    useJUnitPlatform()
}