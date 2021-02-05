import com.rixspi.dependencies.Deps
import com.rixspi.dependencies.Modules

plugins {
    id("java-library")
    id("kotlin")
    id("dependencies")
    id("org.jetbrains.kotlin.plugin.serialization")
}

dependencies {
    implementation(Deps.Kotlin.stdlib)
    implementation(Deps.Kotlin.serialization)

    implementation(Deps.Kotlin.coroutines)

    implementation(project(Modules.Common.domain))
}