plugins {
    // Apply the org.jetbrains.kotlin.jvm Plugin to add support for Kotlin.
    id("org.jetbrains.kotlin.jvm") version "1.4.20"
    id("com.github.johnrengelman.shadow") version "6.1.0"

    // Apply the application plugin to add support for building a CLI application in Java.
    application
}

repositories {
    mavenCentral()
    maven (url = "https://kotlin.bintray.com/kotlinx")
}

dependencies {
    implementation(project(":jardiff"))
}

application {
    mainClassName = "classpathdiff.AppKt"

    // Define the main class for the application.
    mainClass.set("classpathdiff.AppKt")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}
