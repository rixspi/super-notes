import com.rixspi.dependencies.Deps
import com.rixspi.dependencies.Versions.Android
import com.rixspi.dependencies.firebase
import com.rixspi.dependencies.hilt

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
    implementation(Deps.Kotlin.coroutinesAndroid)
    hilt()
    firebase()
    implementation(Deps.mvrx)
}