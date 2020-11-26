package com.fieldcode.commonDomain.scheduler

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class TestSchedulers : Schedulers {
    override fun io(): CoroutineDispatcher = Dispatchers.Unconfined
    override fun background(): CoroutineDispatcher = Dispatchers.Unconfined
    override fun main(): CoroutineDispatcher = Dispatchers.Unconfined
}
