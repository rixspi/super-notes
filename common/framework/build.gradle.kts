import com.rixspi.dependencies.*
import com.rixspi.dependencies.Versions.Android

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("dependencies")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdkVersion(Android.compileSdk)
    buildToolsVersion(Android.buildTools)
    buildFeatures.viewBinding = true
    defaultConfig {
        minSdkVersion(Android.minSdk)
        targetSdkVersion(Android.targetSdk)
        versionCode(Android.versionCode)
        versionName(Android.versionName)

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

dependencies {
    implementation(Deps.Kotlin.stdlib)
    implementation(Deps.Kotlin.coroutines)
    implementation(Deps.Kotlin.coroutinesAndroid)
    hilt()
    firebase()
    implementation(Deps.mvrx)
}