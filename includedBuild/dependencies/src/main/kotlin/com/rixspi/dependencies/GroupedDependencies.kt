package com.rixspi.dependencies

import org.apache.tools.ant.taskdefs.optional.depend.Depend
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
    implementation(Deps.Android.kore)
    implementation(Deps.Android.appCompat)
    implementation(Deps.Android.constraintLayout)
}

fun DependencyHandlerScope.firebase() {
    implementation(platform(Deps.Firebase.platform))
    implementation(Deps.Firebase.playServicesCoroutines)
    implementation(Deps.Firebase.analyticsKtx)
    implementation(Deps.Firebase.auth)
    implementation(Deps.Firebase.firestore)
}

fun DependencyHandlerScope.hilt(){
    implementation(Deps.Hilt.hilt)
    kapt(Deps.Hilt.hiltProcessor)
}

fun DependencyHandlerScope.unitTest() {
    testImplementation(Deps.Test.Unit.junit)
}

fun DependencyHandlerScope.androidTest() {
    androidTestImplementation(Deps.Test.Android.junitExt)
    androidTestImplementation(Deps.Test.Android.espresso)
    androidTestImplementation(Deps.Test.Android.compose)
}

fun DependencyHandlerScope.compose(){
    implementation(Deps.Android.Compose.ui)
    implementation(Deps.Android.Compose.tooling)
    implementation(Deps.Android.Compose.foundation)
    implementation(Deps.Android.Compose.material)
    implementation(Deps.Android.Compose.materialIcons)
    implementation(Deps.Android.Compose.materialIconsExtended)
    implementation(Deps.Android.Compose.activity)
    implementation(Deps.Android.Compose.activityKtx)
}