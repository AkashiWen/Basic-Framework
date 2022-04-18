# common

存放业务无关的基础组件，属于项目最底层Module

## 1. LiveData

### 1.1 Hook非粘性事件的LiveData —— NonStickyLiveData

**反射前确定hook锚点**

hook点1： ``observer.mLastVersion``
路径：
``ObserverWrapper observer``
``Iterator<Map.Entry<Observer<? super T>, ObserverWrapper>> iterator``
``SafeIterableMap<Observer<? super T>, ObserverWrapper> mObservers``

hook点2： ``mVersion``

**开始反射**

``NonStickyLiveData.kt``
...