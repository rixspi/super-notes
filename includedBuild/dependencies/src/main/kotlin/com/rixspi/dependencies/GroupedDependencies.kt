@file: Suppress("TooManyFunctions")

package com.rixspi.dependencies

import com.rixspi.dependencies.Deps.Android
import com.rixspi.dependencies.Deps.Android.Compose
import com.rixspi.dependencies.Deps.Firebase
import com.rixspi.dependencies.Deps.Hilt
import com.rixspi.dependencies.Deps.Test
import com.rixspi.dependencies.Deps.Test.Unit
import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.ProjectDependency
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.DependencyHandlerScope

private fun DependencyHandler.kapt(dependencyNotation: Any): Dependency? =
    add("kapt", dependencyNotation)

private fun DependencyHandler.implementation(dependencyNotation: Any): Dependency? =
    add("implementation", dependencyNotation)

private fun DependencyHandler.androidTestImplementation(dependencyNotation: Any): Dependency? =
    add("androidTestImplementation", dependencyNotation)

private fun DependencyHandler.testImplementation(dependencyNotation: Any): Dependency? =
    add("testImplementation", dependencyNotation)

private fun DependencyHandler.project(
    path: String,
    configuration: String? = null
): ProjectDependency = project(
    if (configuration != null) mapOf("path" to path, "configuration" to configuration)
    else mapOf("path" to path)
) as ProjectDependency

fun DependencyHandlerScope.android() {
    implementation(Android.kore)
    implementation(Android.appCompat)
    implementation(Android.constraintLayout)
}

fun DependencyHandlerScope.firebase() {
    implementation(platform(Firebase.platform))
    implementation(Firebase.playServicesCoroutines)
    implementation(Firebase.analyticsKtx)
    implementation(Firebase.auth)
    implementation(Firebase.firestore)
}

fun DependencyHandlerScope.hilt() {
    implementation(Hilt.hilt)
    kapt(Hilt.hiltProcessor)
}

fun DependencyHandlerScope.unitTest() {
    testImplementation(Unit.junit)
    testImplementation(Unit.mockk)
    testImplementation(Unit.coroutines)
}

fun DependencyHandlerScope.androidTest() {
    androidTestImplementation(Test.Android.junitExt)
    androidTestImplementation(Test.Android.espresso)
    androidTestImplementation(Test.Android.compose)
}

fun DependencyHandlerScope.compose() {
    implementation(Compose.ui)
    implementation(Compose.tooling)
    implementation(Compose.foundation)
    implementation(Compose.material)
    implementation(Compose.materialIcons)
    implementation(Compose.materialIconsExtended)
    implementation(Compose.activity)
    implementation(Compose.activityKtx)
}
