package com.rixspi.dependencies

object Deps {

    val mvrx = "com.airbnb.android:mavericks:${Versions.mvrx}"
    val mvrxCompose = "com.airbnb.android:mavericks-compose:2.1.0-alpha01"

    object Kotlin {
        val stdlib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"
        val serialization =
            "org.jetbrains.kotlinx:kotlinx-serialization-cbor:${Versions.kotlinSerialization}"

        val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
        val coroutinesAndroid =
            "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
    }

    object Android {
        val kore = "androidx.core:core-ktx:${Versions.Android.kore}"
        val appCompat = "androidx.appcompat:appcompat:${Versions.Android.appCompat}"

        val constraintLayout =
            "androidx.constraintlayout:constraintlayout:${Versions.Android.constraint}"

        object Compose {
            val ui = "androidx.compose.ui:ui:${Versions.Android.compose}"

            // Tooling support (Previews, etc.)
            val tooling = "androidx.compose.ui:ui-tooling:${Versions.Android.compose}"

            // Foundation (Border, Background, Box, Image, Scroll, shapes, animations, etc.)
            val foundation = "androidx.compose.foundation:foundation:${Versions.Android.compose}"

            // Material Design
            val material = "androidx.compose.material:material:${Versions.Android.compose}"

            // Material design icons
            val materialIcons =
                "androidx.compose.material:material-icons-core:${Versions.Android.compose}"
            val materialIconsExtended =
                "androidx.compose.material:material-icons-extended:${Versions.Android.compose}"

            val activity = "androidx.activity:activity-compose:${Versions.Android.androidxActivity}"
            val activityKtx = "androidx.activity:activity-ktx:${Versions.Android.androidxActivity}"
            val navigation =
                "androidx.navigation:navigation-compose:${Versions.Android.composeNavigation}"

        }
    }

    object Firebase {
        val platform = "com.google.firebase:firebase-bom:${Versions.firebase}"
        val analyticsKtx = "com.google.firebase:firebase-analytics-ktx"

        val auth = "com.google.firebase:firebase-auth-ktx"
        val firestore = "com.google.firebase:firebase-firestore-ktx"
        val playServicesCoroutines =
            "org.jetbrains.kotlinx:kotlinx-coroutines-play-services:${Versions.firebaseCoroutines}"
    }

    object Hilt {
        val hilt = "com.google.dagger:hilt-android:${Versions.Hilt.hilt}"
        val hiltProcessor = "com.google.dagger:hilt-android-compiler:${Versions.Hilt.hilt}"
    }

    object Test {
        object Unit {
            const val junit = "junit:junit:${Versions.Test.Unit.junit}"
            const val coroutines ="org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}"
            const val mockk = "io.mockk:mockk:${Versions.Test.mockk}"

        }

        object Android {
            const val junitExt = "androidx.test.ext:junit:${Versions.Test.Android.junitExt}"
            const val espresso =
                "androidx.test.espresso:espresso-core:${Versions.Test.Android.espresso}"
            const val compose = "androidx.compose.ui:ui-test-junit4:${Versions.Android.compose}"
            const val mockk = "io.mockk:mockk-android:${Versions.Test.mockk}"
        }
    }
}
