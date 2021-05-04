
import com.rixspi.dependencies.Deps
import com.rixspi.dependencies.Modules
import com.rixspi.dependencies.Versions.Android
import com.rixspi.dependencies.androidTest
import com.rixspi.dependencies.compose
import com.rixspi.dependencies.firebase
import com.rixspi.dependencies.hilt
import com.rixspi.dependencies.unitTest

plugins {
    id("com.android.library")
    id("kotlin-android")

    id("kotlin-kapt")
    id("dependencies")
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

    buildFeatures {
        // Enables Jetpack Compose for this module
        compose = true
    }

    kotlinOptions {
        jvmTarget = "1.8"
        useIR = true
    }
//
    composeOptions {
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
    implementation(Deps.Kotlin.serialization)

    // android()
    firebase()
    compose()
    hilt()

    implementation(project(Modules.Common.framework))
    implementation(project(Modules.Common.data))
    implementation(project(Modules.Common.domain))
    implementation(project(Modules.Common.presentation))

    implementation(project(Modules.Notes.domain))
    implementation(project(Modules.Notes.data))

    implementation(Deps.mvrx)
    implementation(Deps.mvrxCompose)
    //implementation(Deps.Kotlin.coroutinesAndroid)

    unitTest()
    androidTest()
}
