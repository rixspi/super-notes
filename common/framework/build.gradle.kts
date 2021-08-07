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
    compileSdk = Android.compileSdk
    buildToolsVersion = Android.buildTools
    defaultConfig {
        minSdk = Android.minSdk
        targetSdk = Android.targetSdk
        // versionCode(Android.versionCode)
        // versionName(Android.versionName)

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(Deps.Kotlin.coroutinesAndroid)
    hilt()
    firebase()
    implementation(Deps.mvrx)
}
