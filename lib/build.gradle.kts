plugins {
    `java-library`
    antlr
}

repositories {
    mavenCentral()
}

//sourceSets {
//    main {
//        java {
//            srcDirs("generated/java")
//        }
//    }
//}

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

tasks.named<Test>("test") {
    useJUnitPlatform()
}

tasks.named<AntlrTask>("generateGrammarSource") {
    maxHeapSize = "1g"
//    outputDirectory = file("src/generated/java")
    arguments = arguments + listOf("-visitor", "-long-messages")
}
