package com.akashi.common.livedata

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.lang.NullPointerException

/**
 * 非粘性LiveData
 */
class NonStickyLiveData<T> : MutableLiveData<T>() {

    /**
     * 默认false
     * 让hook在第一次observe生效
     */
    private var stickyFlag = false

    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        super.observe(owner, observer)
        if (!stickyFlag) {
            this.hook(observer)
        }
    }

    private fun hook(observer: Observer<in T>) {
        try {
//            hook点1： ``observer.mLastVersion``
//            路径（从下往上获取）：
//            ``ObserverWrapper observer``
//            ``Iterator<Map.Entry<Observer<? super T>, ObserverWrapper>> iterator``
//            ``SafeIterableMap<Observer<? super T>, ObserverWrapper> mObservers``

            val liveDataCls = LiveData::class.java
            val mObserversField = liveDataCls.getDeclaredField("mObservers").also {
                it.isAccessible = true
            }
            // 获取到这个成员变量的对象
            val mObservers = mObserversField.get(this)
            // 得到map对应的class对象
            val mObserversClass = mObservers::class.java
            // 获取mObservers对象的get方法
            val get = mObserversClass.getDeclaredMethod("get", Object::class.java).also {
                it.isAccessible = true
            }
            // 相当于等待执行val observerWrapper: ObserverWrapper = mObservers.get(observer)
            val invokeEntry = get.invoke(mObservers, observer) as? Map.Entry<*, *>
                ?: throw NullPointerException("get.invoke(mObserversObject, observer) cast to Map.Entry<*,*> failed.!")
            val observerWrapper =
                invokeEntry.value ?: throw NullPointerException("observerWrapper is null.!")
            // superClass 是Observer类 **
            val superClass = observerWrapper.javaClass.superclass
            val mLastVersion = superClass.getDeclaredField("mLastVersion").also {
                it.isAccessible = true
            }
            // 2. 得到mVersion
            val mVersionField = liveDataCls.getDeclaredField("mVersion").also {
                it.isAccessible = true
            }
            // 3. mVersion的值赋值到mLastVersion
            val mVersion = mVersionField.get(this)
            mLastVersion.set(observerWrapper, mVersion)

            stickyFlag = true
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }
}