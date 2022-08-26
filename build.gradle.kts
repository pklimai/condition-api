val ktor_version = "2.1.0"
val kotlin_version = "1.7.10"
val logback_version = "1.2.11"

plugins {
    application
    kotlin("jvm") version "1.7.10"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.7.10"
}

group = "api.condition.nica.npm.mipt.ru"
version = "0.0.1"
application {
    mainClass.set("api.condition.nica.npm.mipt.ru.ApplicationKt")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-core:$ktor_version")
    implementation("io.ktor:ktor-server-content-negotiation:$ktor_version")
    implementation("io.ktor:ktor-server-compression:$ktor_version")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor_version")
    implementation("io.ktor:ktor-server-netty:$ktor_version")
    implementation("io.ktor:ktor-server-html-builder:$ktor_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")

    implementation("org.jetbrains.exposed:exposed:0.17.13")
    implementation("org.postgresql:postgresql:42.5.0")
    implementation("org.jetbrains.kotlinx:kotlinx-html:0.7.3")

    testImplementation("io.ktor:ktor-server-tests:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test:$kotlin_version")
}
