@file: Suppress("TooManyFunctions")

package com.rixspi.domain

sealed class Error(open val throwable: Throwable? = null) {
    data class UnspecifiedError(override val throwable: Throwable? = null) : Error(throwable)

    data class ElementNotFound(override val throwable: Throwable? = null) : Error(throwable)
}

fun Exception.toError(): Error {
    return Error.UnspecifiedError(this)
}

sealed class Result<out T> {

    data class Success<out T>(val data: T) : Result<T>()
    data class Failure(val error: Error) : Result<Nothing>()

    operator fun invoke(): T? = (this as? Success)?.data
}

inline infix fun <T> Result<T>.mapError(f: (Error) -> Error): Result<T> = when (this) {
    is Result.Failure -> Result.Failure(f(error))
    is Result.Success -> this
}

inline infix fun <T, V2> Result<T>.map(f: (T) -> V2): Result<V2> = when (this) {
    is Result.Failure -> this
    is Result.Success -> Result.Success(f(data))
}

inline infix fun <T, V2> Result<T>.mapCatching(f: (T) -> V2): Result<V2> = when (this) {
    is Result.Failure -> this
    is Result.Success -> safeCall { f(data) }
}

inline infix fun <T, V2> Result<Iterable<T>>.mapEach(f: (T) -> V2): Result<List<V2>> =
    when (this) {
        is Result.Failure -> this
        is Result.Success -> Result.Success(data.map(f))
    }

inline infix fun <T, V2> Result<T>.flatMap(f: (T) -> Result<V2>): Result<V2> = when (this) {
    is Result.Failure -> this
    is Result.Success -> f(data)
}

/**
 * Method only for side effects, it will just run the block passed to it when result is
 * a success.
 *
 * NOT CHANGING STATE, NO PASSING DOWN THE EVENTUAL ERROR
 */
inline infix fun <T> Result<T>.doOnSuccess(f: (T) -> Unit): Result<T> {
    if (this is Result.Success) {
        safeCall { f(data) }
    }
    return this
}

/**
 * Method only for side effects, it will just run the block passed to it when result is
 * an error.
 *
 * NOT CHANGING STATE, NO PASSING DOWN THE EVENTUAL ERROR
 */
inline infix fun <T> Result<T>.doOnError(f: (Error) -> Unit): Result<T> {
    if (this is Result.Failure) {
        safeCall { f(error) }
    }
    return this
}

infix fun <T, V2> Result<(T) -> V2>.apply(f: Result<T>): Result<V2> = when (this) {
    is Result.Failure -> this
    is Result.Success -> f.map(this.data)
}

@Suppress("UNCHECKED_CAST")
inline fun <T, A> Result<T>.fold(
    error: (Error) -> A = { this as A },
    success: (T) -> A = { this as A }
): A =
    when (this) {
        is Result.Failure -> error(this.error)
        is Result.Success -> success(this.data)
    }

fun <T> success(data: T) = Result.Success(data)

fun failure(error: Error) = Result.Failure(error)

@Suppress("TooGenericExceptionCaught")
inline fun <T> safeCall(call: () -> T): Result<T> =
    try {
        Result.Success(call.invoke())
    } catch (exception: Exception) {
        Result.Failure(exception.toError())
    }
