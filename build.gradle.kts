import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.32"
}

group = "me.marius"
version = "0.1"

repositories {
    mavenCentral()
}
tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.3")
    implementation("no.tornado:tornadofx:1.7.20")
    implementation("org.locationtech.jts:jts-core:1.16.1")
}
