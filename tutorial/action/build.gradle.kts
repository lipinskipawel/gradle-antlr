import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    application
    antlr
    id("com.github.johnrengelman.shadow") version "7.0.0"
}

repositories {
    mavenCentral()
}

dependencies {
    antlr("org.antlr:antlr4:4.13.1")

    testImplementation(libs.junit.jupiter)
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation("org.assertj:assertj-core:3.20.2")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

application {
    mainClass.set("com.github.lipinskipawel.tutorial.app.Application")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}

tasks.named<AntlrTask>("generateGrammarSource") {
    maxHeapSize = "1g"
    arguments = arguments + listOf("-no-listener", "-long-messages")
}

tasks.withType<ShadowJar> {
    isZip64 = true
    mergeServiceFiles()
    manifest {
        attributes["Main-Class"] = application.mainClass
    }
}
