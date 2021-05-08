
import com.rixspi.dependencies.Deps
import com.rixspi.dependencies.Modules
import com.rixspi.dependencies.Versions.Android
import com.rixspi.dependencies.android
import com.rixspi.dependencies.androidTest
import com.rixspi.dependencies.compose
import com.rixspi.dependencies.hilt
import org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED
import org.gradle.api.tasks.testing.logging.TestLogEvent.PASSED
import org.gradle.api.tasks.testing.logging.TestLogEvent.SKIPPED
import org.gradle.api.tasks.testing.logging.TestLogEvent.STANDARD_ERROR
import org.gradle.api.tasks.testing.logging.TestLogEvent.STANDARD_OUT

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("com.google.gms.google-services")  // Google Services plugin
    id("com.google.firebase.crashlytics")
    id("org.jetbrains.kotlin.plugin.serialization")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("dependencies")
}

android {
    compileSdkVersion(Android.compileSdk)
    buildToolsVersion(Android.buildTools)
    buildFeatures.viewBinding = true
    defaultConfig {
        applicationId = Android.applicationId
        minSdkVersion(Android.minSdk)
        targetSdkVersion(Android.targetSdk)
        // versionCode(Android.versionCode)
        // versionName(Android.versionName)

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        maybeCreate("release").apply {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
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

    hilt {
        enableExperimentalClasspathAggregation = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Android.compose
    }

    testOptions {
        animationsDisabled = true
        execution = "ANDROIDX_TEST_ORCHESTRATOR"
        // Changes the directory where Gradle saves test reports. By default, Gradle saves test reports
        // in the path_to_your_project/module_name/build/outputs/reports/ directory.
        // '$rootDir' sets the path relative to the root directory of the current project.
        reportDir = "$rootDir/reports/androidTest/${project.name}"
        // Changes the directory where Gradle saves test results. By default, Gradle saves test results
        // in the path_to_your_project/module_name/build/outputs/test-results/ directory.
        // '$rootDir' sets the path relative to the root directory of the current project.
        resultsDir = "$rootDir/reports/androidTest/results/${project.name}"

        unitTests.isReturnDefaultValues = true
        unitTests.all {
            with(it) {
                reports {
                    junitXml.destination = file("$rootDir/reports/${project.name}/xml")
                    html.isEnabled = true
                    html.destination = file("$rootDir/reports/${project.name}/html")
                }

                jvmArgs = listOf("-XX:MaxPermSize=512m")

                testLogging {
                    events = setOf(PASSED, SKIPPED, FAILED, STANDARD_OUT, STANDARD_ERROR)
                }
            }
        }
    }

    packagingOptions {
        with(resources.excludes) {
            add("META-INF/LICENSE")
            add("META-INF/DEPENDENCIES")
            add("META-INF/LICENSE.txt")
            add("META-INF/license.txt")
            add("META-INF/NOTICE")
            add("META-INF/NOTICE.txt")
            add("META-INF/notice.txt")
            add("META-INF/ASL2.0")
            add("META-INF/*.kotlin_module")
            add("**/attach_hotspot_windows.dll")
            add("META-INF/AL2.0")
            add("META-INF/LGPL2.1")
            add("META-INF/licenses/**")
        }
    }
}

dependencies {
    // implementation(Deps.Kotlin.serialization)
    hilt()
    android()
    compose()
    implementation(Deps.Android.Compose.navigation)
    implementation(Deps.mvrx)

    implementation(project(Modules.Notes.presentation))
    implementation(project(Modules.Common.presentation))

    androidTest()
}
