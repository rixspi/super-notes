rootProject.name = "SuperNotes-android"
includeBuild("includedBuild/dependencies")

pluginManagement {
    repositories {
        google()
        jcenter()
        gradlePluginPortal()
    }
}

include(":app")
include(":framework")

include(":common:data")
include(":common:domain")
include(":common:presentation")

include(":notes:data")
include(":notes:domain")
include(":notes:presentation")

