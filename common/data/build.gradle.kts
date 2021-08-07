
import com.rixspi.dependencies.Deps
import com.rixspi.dependencies.Modules

plugins {
    id("java-library")
    id("kotlin")
    id("dependencies")
    id("org.jetbrains.kotlin.plugin.serialization")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().all {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(project(Modules.Common.domain))

    implementation(Deps.Kotlin.serialization)
}