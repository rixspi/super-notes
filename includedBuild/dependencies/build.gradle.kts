plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
}

// To make it available as direct dependency
group = "com.rixspi.dependencies"
version = "SNAPSHOT"

repositories {
    mavenCentral()
}

gradlePlugin {
    plugins.register("dependencies") {
        id = "dependencies"
        implementationClass = "com.rixspi.dependencies.DepsPlugin"
    }
}