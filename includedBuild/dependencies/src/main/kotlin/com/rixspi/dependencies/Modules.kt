package com.rixspi.dependencies

object Modules {
    const val app = ":app"

    object Common {
        const val data = ":common:data"
        const val domain = ":common:domain"
        const val presentation = ":common:presentation"
        const val framework = ":common:framework"
    }

    object Notes {
        const val data = ":notes:data"
        const val domain = ":notes:domain"
        const val presentation = ":notes:presentation"
    }
}