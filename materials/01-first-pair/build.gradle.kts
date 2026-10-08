plugins {
    kotlin("jvm") version "2.2.21"
    application
}

group = "com.example"
version = "1.0.0"

repositories { mavenCentral() }

val ktorVersion = "3.3.3"
dependencies {
    implementation("io.ktor:ktor-server-core-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-netty-jvm:$ktorVersion")
    implementation("ch.qos.logback:logback-classic:1.5.21")
    testImplementation("io.ktor:ktor-server-test-host-jvm:$ktorVersion")
    testImplementation(kotlin("test"))
}

kotlin { jvmToolchain(21) }
application { mainClass.set("com.example.ApplicationKt") }
tasks.test { useJUnitPlatform() }
