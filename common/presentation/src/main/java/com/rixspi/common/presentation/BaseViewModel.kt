package com.rixspi.common.presentation

import com.airbnb.mvrx.*
import com.rixspi.domain.Error
import com.rixspi.domain.Result
import com.rixspi.domain.fold
import com.rixspi.domain.interactor.SuspendUseCase
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.reflect.KProperty1

@OptIn(InternalMavericksApi::class)
open class BaseViewModel<S : MavericksState>(
    private val initialState: S
) : MavericksViewModel<S>(initialState) {

    private fun <T> Result<T>.toAsync(): Async<T> = fold(
        error = {
            // TODO Change this hardcoded exception
            Fail(IllegalStateException())
        },
        success = {
            Success(it)
        }
    )

    private fun <T, A> Result<T>.toAsync(mapper: ((T) -> A)): Async<A> = fold(
        error = {
            // TODO Change this hardcoded exception
            Fail(IllegalStateException())
        },
        success = {
            Success(mapper(it))
        }
    )

    protected fun <A, T : Result<A>, V> Flow<T>.execute(
        dispatcher: CoroutineDispatcher? = null,
        retainValue: KProperty1<S, Async<V>>? = null,
        mapper: ((A) -> V),
        reducer: S.(Async<V>) -> S
    ): Job {
        val blockExecutions = config.onExecute(this@BaseViewModel)
        if (blockExecutions != MavericksViewModelConfig.BlockExecutions.No) {
            if (blockExecutions == MavericksViewModelConfig.BlockExecutions.WithLoading) {
                setState { reducer(Loading(value = retainValue?.get(this)?.invoke())) }
            }
            // Simulate infinite loading
            return viewModelScope.launch { delay(Long.MAX_VALUE) }
        }

        setState { reducer(Loading(value = retainValue?.get(this)?.invoke())) }
        return catch { error ->
            setState {
                reducer(
                    Fail(
                        error,
                        value = retainValue?.get(this)?.invoke()
                    )
                )
            }
        }
            .onEach { value -> setState { reducer(value.toAsync(mapper)) } }
            .launchIn(viewModelScope + (dispatcher ?: EmptyCoroutineContext))
    }

    @JvmName("executeJava")
    protected fun <A, T : Result<A>, V> (suspend () -> T).execute(
        dispatcher: CoroutineDispatcher? = null,
        retainValue: KProperty1<S, Async<V>>? = null,
        mapper: ((A) -> V),
        reducer: S.(Async<V>) -> S
    ): Job {
        val blockExecutions = config.onExecute(this@BaseViewModel)
        if (blockExecutions != MavericksViewModelConfig.BlockExecutions.No) {
            if (blockExecutions == MavericksViewModelConfig.BlockExecutions.WithLoading) {
                setState { reducer(Loading()) }
            }
            // Simulate infinite loading
            return viewModelScope.launch { delay(Long.MAX_VALUE) }
        }

        setState { reducer(Loading(value = retainValue?.get(this)?.invoke())) }

        return viewModelScope.launch(dispatcher ?: EmptyCoroutineContext) {
            try {
                val result = invoke()
                setState { reducer(result.toAsync(mapper)) }
            } catch (e: CancellationException) {
                @Suppress("RethrowCaughtException")
                throw e
            } catch (@Suppress("TooGenericExceptionCaught") e: Exception) {
                setState { reducer(Fail(e, value = retainValue?.get(this)?.invoke())) }
            }
        }
    }

    @JvmName("executeJava")
    protected fun <A, T : Result<A>> (suspend () -> T).execute(
        dispatcher: CoroutineDispatcher? = null,
        retainValue: KProperty1<S, Async<A>>? = null,
        reducer: S.(Async<A>) -> S
    ): Job {
        val blockExecutions = config.onExecute(this@BaseViewModel)
        if (blockExecutions != MavericksViewModelConfig.BlockExecutions.No) {
            if (blockExecutions == MavericksViewModelConfig.BlockExecutions.WithLoading) {
                setState { reducer(Loading()) }
            }
            // Simulate infinite loading
            return viewModelScope.launch { delay(Long.MAX_VALUE) }
        }

        setState { reducer(Loading(value = retainValue?.get(this)?.invoke())) }

        return viewModelScope.launch(dispatcher ?: EmptyCoroutineContext) {
            try {
                val result = invoke()
                setState { reducer(result.toAsync()) }
            } catch (e: CancellationException) {
                @Suppress("RethrowCaughtException")
                throw e
            } catch (@Suppress("TooGenericExceptionCaught") e: Exception) {
                setState { reducer(Fail(e, value = retainValue?.get(this)?.invoke())) }
            }
        }
    }

    protected fun <A, B, V> SuspendUseCase<A, B>.execute(
        dispatcher: CoroutineDispatcher? = null,
        retainValue: KProperty1<S, Async<V>>? = null,
        mapper: ((B) -> V),
        params: A,
        reducer: S.(Async<V>) -> S
    ) {
        suspend { this(parameters = params) }.execute(
            dispatcher = dispatcher,
            retainValue = retainValue,
            mapper = mapper,
            reducer = reducer
        )
    }

    protected fun <A, B> SuspendUseCase<A, B>.execute(
        dispatcher: CoroutineDispatcher? = null,
        params: A,
        reducer: S.(Async<B>) -> S
    ) {
        suspend { this(parameters = params) }.execute(
            dispatcher = dispatcher,
            retainValue = null,
            reducer = reducer
        )
    }

}
