package com.rixspi.supernotes.domain.common

import arrow.core.Either


sealed class Error {

}

typealias Result<T> = Either<Error, T>