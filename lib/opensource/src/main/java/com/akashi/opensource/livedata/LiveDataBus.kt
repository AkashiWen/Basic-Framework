package com.akashi.opensource.livedata

import androidx.lifecycle.MutableLiveData

/**
 * 总线bus
 * 基于LiveData
 */
class LiveDataBus private constructor() {

    companion object {
        private val instance by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { LiveDataBus() }

        fun get() = instance
    }

    private val bus = HashMap<String, MutableLiveData<Any>>()

    @Suppress("UNCHECKED_CAST")
    @Synchronized
    fun <T> with(event: String, type: Class<T>, sticky: Boolean = true): MutableLiveData<T> {
        if (!bus.containsKey(event)) {
            if (sticky) {
                bus[event] = MutableLiveData()
            } else {
                bus[event] = NonStickyLiveData()
            }
        }
        return bus[event] as MutableLiveData<T>
    }

}