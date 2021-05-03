import com.rixspi.dependencies.Deps
import com.rixspi.dependencies.Modules

plugins {
    id("java-library")
    id("kotlin")
    id("dependencies")
    id("org.jetbrains.kotlin.plugin.serialization")
}

dependencies {
    implementation(project(Modules.Common.domain))

    implementation(Deps.Kotlin.serialization)
}