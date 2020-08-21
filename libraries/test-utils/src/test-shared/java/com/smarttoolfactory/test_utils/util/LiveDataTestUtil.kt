package com.smarttoolfactory.test_utils.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

/**
 * Gets the value of a [LiveData] or waits for it to have one, with a timeout.
 *
 * Use this extension from host-side (JVM) tests. It's recommended to use it alongside
 * `InstantTaskExecutorRule` or a similar mechanism to execute tasks synchronously.
 */
fun <T> LiveData<T>.getOrAwaitValue(
    time: Long = 2,
    timeUnit: TimeUnit = TimeUnit.SECONDS,
    afterObserve: () -> Unit = {}
): T {

    var data: T? = null
    val latch = CountDownLatch(1)

    val observer = object : Observer<T> {
        override fun onChanged(o: T?) {
            data = o
            latch.countDown()
            this@getOrAwaitValue.removeObserver(this)
        }
    }

    this.observeForever(observer)

    afterObserve.invoke()

    // Don't wait indefinitely if the LiveData is not set.
    if (!latch.await(time, timeUnit)) {
        this.removeObserver(observer)
        throw TimeoutException("LiveData value was never set.")
    }

    @Suppress("UNCHECKED_CAST")
    return data as T
}

/**
 * RxJava2 style [Observer] for [LiveData] to test multiple values or states in container.
 *
 * This class is useful for testing view or action states or order of states if you are using
 * stateful machine.
 *
 * * Use with `InstantTaskExecutorRule` or a similar mechanism to execute tasks synchronously.
 *
 */
class LiveDataTestObserver<T> constructor(
    private val liveData: LiveData<T>
) : Observer<T> {

    init {
        liveData.observeForever(this)
    }

    private val testValues = mutableListOf<T>()

    override fun onChanged(t: T) {
        if (t != null) testValues.add(t)
    }

    fun assertNoValues(): LiveDataTestObserver<T> {
        if (testValues.isNotEmpty()) throw AssertionError(
            "Assertion error with actual size ${testValues.size}"
        )
        return this
    }

    fun assertValueCount(count: Int): LiveDataTestObserver<T> {
        if (count < 0) throw AssertionError(
            "Assertion error! value count cannot be smaller than zero"
        )
        if (count != testValues.size) throw AssertionError(
            "Assertion error! with expected $count while actual ${testValues.size}"
        )
        return this
    }

    fun assertValues(vararg predicates: T): LiveDataTestObserver<T> {
        if (!testValues.containsAll(predicates.asList())) throw AssertionError("Assertion error!")
        return this
    }

    fun assertValues(predicate: (List<T>) -> Boolean): LiveDataTestObserver<T> {
        predicate(testValues)
        return this
    }

    fun values(predicate: (List<T>) -> Unit): LiveDataTestObserver<T> {
        predicate(testValues)
        return this
    }

    fun values(): List<T> {
        return testValues
    }

    /**
     * Removes this observer from the [LiveData] which was observing
     */
    fun dispose() {
        liveData.removeObserver(this)
    }

    /**
     * Clears data available in this observer and removes this observer from the [LiveData] which was observing
     */
    fun clear() {
        testValues.clear()
        dispose()
    }
}

fun <T> LiveData<T>.test(): LiveDataTestObserver<T> {

    val testObserver = LiveDataTestObserver(this)

    // Remove this testObserver that is added in init block of TestObserver, and clears previous data
    testObserver.clear()
    observeForever(testObserver)

    return testObserver
}
