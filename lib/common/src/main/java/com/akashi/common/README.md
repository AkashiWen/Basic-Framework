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

### 1.2 LiveDataBus

提供粘性和非粘性注册

## 2. Hook

### 2.1 hook ams

`/hook/HookUtil.kt`

## 3. 外置存储卡存储框架

### 3.1 针对Android11 R 30 外置卡沙箱机制

`/file/FileAccessFactory.kt`

工厂模式根据api版本提供具体实现类