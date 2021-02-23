import com.rixspi.dependencies.Deps

plugins {
    id("java-library")
    id("kotlin")
    id("kotlin-kapt")
    id("dependencies")
    id("org.jetbrains.kotlin.plugin.serialization")
}


dependencies {
    implementation(Deps.Kotlin.serialization)
    implementation(Deps.Kotlin.coroutines)

}