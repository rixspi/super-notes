import com.rixspi.dependencies.*
import com.rixspi.dependencies.Versions.Android

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-android-extensions")

    id("org.jetbrains.kotlin.plugin.serialization")
    id("kotlin-kapt")
    id("dependencies")
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

    buildFeatures {
        // Enables Jetpack Compose for this module
        compose = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "1.8"
        useIR = true
    }

    composeOptions {
        @Suppress("DEPRECATION")
                kotlinCompilerVersion = "1.4.21"
        kotlinCompilerExtensionVersion = Android.compose
    }

    packagingOptions {
        exclude("META-INF/DEPENDENCIES")
        exclude("META-INF/LICENSE")
        exclude("META-INF/LICENSE.txt")
        exclude("META-INF/license.txt")
        exclude("META-INF/NOTICE")
        exclude("META-INF/NOTICE.txt")
        exclude("META-INF/notice.txt")
        exclude("META-INF/ASL2.0")
        exclude("META-INF/*.kotlin_module")
    }
}

dependencies {
    implementation(Deps.Kotlin.stdlib)
    implementation(Deps.Kotlin.serialization)

    android()
    firebase()
    compose()
    implementation(Deps.mvrx)

    implementation(Deps.Kotlin.coroutines)
    implementation(Deps.Kotlin.coroutinesAndroid)

    unitTest()
    androidTest()
}