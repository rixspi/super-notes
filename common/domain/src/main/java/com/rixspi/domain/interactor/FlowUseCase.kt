package com.rixspi.domain.interactor

import com.rixspi.domain.Result
import com.rixspi.domain.toError
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn

/**
 * Executes business logic in its execute method and keep posting updates to the result as
 * [Result<R>].
 * Handling an exception (emit [Result.Error] to the result) is the subclasses's responsibility.
 */
abstract class FlowUseCase<in P, R>(private val coroutineDispatcher: CoroutineDispatcher) {

    @ExperimentalCoroutinesApi
    operator fun invoke(parameters: P): Flow<Result<R>> {
        return run(parameters)
            .catch { e -> emit(Result.Failure(Exception(e).toError())) }
            .flowOn(coroutineDispatcher)
    }

    abstract fun run(parameters: P): Flow<Result<R>>
}

/**
 * Executes business logic in its execute method and keep posting updates to the result as
 * [Result<R>].
 * Handling an exception (emit [Result.Error] to the result) is the subclasses's responsibility.
 */
abstract class FlowParameterLessUseCase<R>(private val coroutineDispatcher: CoroutineDispatcher) {

    @ExperimentalCoroutinesApi
    operator fun invoke(): Flow<Result<R>> {
        return run()
            .catch { e -> emit(Result.Failure(Exception(e).toError())) }
            .flowOn(coroutineDispatcher)
    }

    abstract fun run(): Flow<Result<R>>
}