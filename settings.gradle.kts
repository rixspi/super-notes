rootProject.name = "SuperNotes-android"
includeBuild("includedBuild/dependencies")

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

include(":app")

include(":common:framework")
include(":common:data")
include(":common:domain")
include(":common:presentation")

include(":notes:data")
include(":notes:domain")
include(":notes:presentation")
