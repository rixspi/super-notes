import com.rixspi.dependencies.Deps
import com.rixspi.dependencies.Modules

plugins {
    id("kotlin")
    id("dependencies")
    id("org.jetbrains.kotlin.plugin.serialization")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
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
