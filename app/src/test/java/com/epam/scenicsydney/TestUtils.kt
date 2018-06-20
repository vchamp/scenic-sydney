package com.epam.scenicsydney

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import org.mockito.Mockito
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

fun <T> LiveData<T>.waitForValue(condition: (T?) -> Boolean = { true }): T? {
    var data: T? = null
    val latch = CountDownLatch(1)
    val observer = object : Observer<T> {
        override fun onChanged(o: T?) {
            if (condition(o)) {
                data = o
                latch.countDown()
                this@waitForValue.removeObserver(this)
            }
        }
    }
    observeForever(observer)
    latch.await(2, TimeUnit.SECONDS)
    return data
}

fun <T> any(): T {
    Mockito.any<T>()
    return uninitialized()
}

fun <T> uninitialized(): T = null as T
