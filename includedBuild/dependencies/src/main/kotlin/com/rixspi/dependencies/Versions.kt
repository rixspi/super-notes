package com.rixspi.dependencies

@Suppress("MayBeConst", "MemberNameEqualsClassName")
object Versions {
    const val kotlin = "1.4.30"
    const val kotlinSerialization = "1.0.1"
    const val coroutines = "1.4.0"

    const val mvrx = "2.1.0"
    const val mvrxCompose = "2.1.0-alpha01"

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
        const val androidxActivity = "1.3.0-alpha03"

        const val constraint = "2.0.4"

        const val compose = "1.0.0-beta01"
        const val composeNavigation = "1.0.0-alpha08"
    }

    object Hilt {
        const val hilt = "2.35"
    }

    object Test {
        const val mockk = "1.10.6"
        const val flowTurbine = "0.4.1"

        object Unit {
            const val junit = "4.13.1"
        }

        object Android {
            const val junitExt = "1.1.2"
            const val espresso = "3.3.0"
        }
    }

    const val firebase = "27.1.0"
    const val firebaseCoroutines = "1.4.2"
}
