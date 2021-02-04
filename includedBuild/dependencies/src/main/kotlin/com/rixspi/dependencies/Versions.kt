package com.rixspi.dependencies

object Versions {
    const val kotlin = "1.4.21"
    const val kotlinSerialization = "1.0.1"
    const val coroutines = "1.4.0"

    const val mvrx = "2.0.0"

    object Android {
        const val compileSdk = 30
        const val buildTools = "30.0.2"
        const val applicationId = "com.rixspi.supernotes"
        const val minSdk = 21
        const val targetSdk = 30
        const val versionCode = 1
        const val versionName = "1.0"

        const val kore = "1.3.2"
        const val appCompat = "1.2.0"

        const val constraint = "2.0.4"

        const val compose = "1.0.0-alpha10"
        const val composeNavigation = "1.0.0-alpha06"
    }

    object Hilt {
        const val hilt = "2.31.1-alpha"
    }

    object Test {
        object Unit {
            const val junit = "4.13.1"
        }

        object Android {
            const val junitExt = "1.1.2"
            const val espresso = "3.3.0"
        }
    }

    const val firebase = "25.12.0"
    const val firebaseCoroutines = "1.4.2"
}