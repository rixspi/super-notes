import com.rixspi.dependencies.*

plugins {
    id("kotlin")
    id("dependencies")
    id("org.jetbrains.kotlin.plugin.serialization")
}

sourceSets {
    val sharedTestDir = project(Modules.Notes.domain).file("src/test/java")
    test {
        java.srcDir(sharedTestDir)
    }
}

dependencies {
    implementation(Deps.Kotlin.serialization)

    implementation(project(Modules.Notes.domain))
    implementation(project(Modules.Common.data))
    implementation(project(Modules.Common.domain))

    implementation(Deps.Kotlin.coroutines)

    testImplementation(Deps.Test.Unit.junit)
    testImplementation(Deps.Test.Unit.flowTurbine)
    testImplementation(Deps.Test.Unit.mockk)
    testImplementation(Deps.Test.Unit.coroutines)
}