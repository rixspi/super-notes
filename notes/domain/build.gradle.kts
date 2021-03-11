import com.rixspi.dependencies.Deps
import com.rixspi.dependencies.Modules

plugins {
    id("java-library")
    id("kotlin")
    id("dependencies")
    id("org.jetbrains.kotlin.plugin.serialization")
}

dependencies {
    implementation(Deps.Kotlin.serialization)

    implementation(Deps.Kotlin.coroutines)

    implementation(project(Modules.Common.domain))

    implementation(Deps.Test.Unit.junit)
    implementation(Deps.Test.Unit.coroutines)
    implementation(Deps.Test.Unit.mockk)
}