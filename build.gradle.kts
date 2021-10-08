val ktorVersion = "1.6.3"
val kotlinVersion = "1.5.31"
val logbackVersion = "1.2.5"
val exposedVersion = "0.34.1"

plugins {
    application
    kotlin("jvm") version "1.5.21"
}

group = "uk.co.thecookingpot"
version = "0.0.1"
application {
    mainClass.set("io.ktor.server.netty.EngineMain")
}

repositories {
    mavenLocal()
    jcenter()
    maven { url = uri("https://kotlin.bintray.com/ktor") }
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-gson:$ktorVersion")
    implementation("io.ktor:ktor-server-netty:$ktorVersion")
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    implementation("io.ktor:ktor-server-core:$ktorVersion")
    implementation("io.ktor:ktor-auth:$ktorVersion")
    implementation("io.ktor:ktor-auth-jwt:$ktorVersion")
    implementation("io.ktor:ktor-html-builder:$ktorVersion")
    implementation("com.nimbusds:nimbus-jose-jwt:9.11.3")
    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
    implementation("com.zaxxer:HikariCP:5.0.0")
    implementation("org.postgresql:postgresql:42.2.23")
    implementation("redis.clients:jedis:3.7.0")
    testImplementation("io.ktor:ktor-server-tests:$ktorVersion")
}