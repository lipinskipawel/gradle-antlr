plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

rootProject.name = "gradle-antlr"
include("lib")

include(":tutorial:action")
project(":tutorial:action").projectDir = file("tutorial/action")

include(":tutorial:visitor")
project(":tutorial:visitor").projectDir = file("tutorial/visitor")
