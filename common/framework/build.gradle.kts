import com.rixspi.dependencies.*
import com.rixspi.dependencies.Versions.Android

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("dependencies")
    kotlin("kapt")
}

android {
    compileSdkVersion(Android.compileSdk)
    buildToolsVersion(Android.buildTools)
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
    implementation(Deps.Kotlin.coroutinesAndroid)
    hilt()
    firebase()
    implementation(Deps.mvrx)
}