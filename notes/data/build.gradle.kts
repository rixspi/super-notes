import com.rixspi.dependencies.*

plugins {
    id("kotlin")
    id("dependencies")
    id("org.jetbrains.kotlin.plugin.serialization")
}

dependencies {
    implementation(Deps.Kotlin.stdlib)
    implementation(Deps.Kotlin.serialization)

    implementation(project(Modules.Notes.domain))
    implementation(project(Modules.Common.data))
    implementation(project(Modules.Common.domain))

    implementation(Deps.Kotlin.coroutines)
}