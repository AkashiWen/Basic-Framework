# 项目基础架构探索实现

## 1. MVP实现线索

1. view
2. presenter
3. model

> 持有顺序：UI -> interface view -> presenter -> model
> 回调顺序：model -> presenter -> interface view callback -> UI

### 1.1 抽象

- BaseModel

```kotlin

interface IBaseModel<B> {
    fun fetch(listener: LoadListener)
    interface LoadListener {
        fun onComplete(bean: B)
        fun onError(msg: String)
    }
}

```

- BaseView

```kotlin
interface IBaseView {
    fun showError()
    // ...
}
```

- BasePresenter
    - 弱引用根除Activity内存泄漏问题
    - 使用Lifecycle感知生命周期

```kotlin
protected class BasePresenter<V : BaseView> : LifecycleObserver {
    val iView: WeakReference<V>? = null
    fun attachView(v: V) {}
    fun detachView() {}
}
```

- BaseActivity
    - 利用attachView()连接Presenter和View

```kotlin
protected abstract class BaseActivity<P : BasePresenter<V>, V : IBaseView> : AppCompactActivity() {
    abstract fun createPresenter(): P
    protected lateinit var presenter: P

    override fun onCreate(savedInstanceState: Bundle?) {
        this.presenter = createPresenter()
        this.presenter.attachView(this as V)
    }
}

```

### 1.2 利用Lifecycle感知生命周期

- 被观察者：实现LifecycleOwner
    - Activity、Fragment
- 观察者：继承LifecycleObserver
    - 各种Presenter

## 2. App启动框架（任务调度系统）

> 技术研究向复杂版，轻量级别无视，或者使用[android-startup](https://github.com/idisfkj/android-startup)

### 2.1 拓扑排序算法

**算法实现**
``TopologySort.kt``

**单元测试**
``TopologySortTest.kt``

```
===== 拓扑排序结果 ====
SafetyCheckTask =>PrivacyTask =>DataBaseTask =>SDKTask =>MiddlewareTask
===== 拓扑依赖关系图 ====
PrivacyTask <= DataBaseTask, SDKTask, 
SDKTask <= MiddlewareTask, 
DataBaseTask <= MiddlewareTask, 
SafetyCheckTask <= PrivacyTask, 
```

### 2.2 加入CountDownLatch实现对启动任务的并发控制

**接口**
``Dispatcher.kt``

- 提供运行线程选择
- 提供线程池选择
- 支持设置线程优先级

**默认实现**
抽象类``AndroidStartup.kt``
> 持有一个CountDownLatch对象

**线程池管理**
``ExecutorManager.kt``

提供三种线程池

- cpu密集型
- io密集型
- main主线程

**线程任务封装**
``StartupRunnable.kt``

**启动入口**
``StartupManager.kt``

> 遗留问题：countDownLatch初始化和赋值

### 2.3 加入ContentProvider优化启动调用

**优化结果**
只需要在manifest中配置末端任务，程序会自动查找父任务依次执行

``StartupProvider.kt``

- 在Application#onCreate()之前执行

``StartupInitializer.kt``

- PMS 读取Provider#META-DATA
- 递归遍历父任务

### 3. 总线

`/common/LiveDataBus.kt`

### 4. 对象注入 Koin dagger2 Hlit 等

Koin

### 5. 组件化

### 6. hook工具

`/common/hook/HookUtil.kt`

### 7. 插件化

`/kotlin_plugin/CustomKotlinPlugin`

### 8. 全局Crash&Native&ANR异常捕获

[iqiyi/xcrash](https://github.com/iqiyi/xCrash/blob/master/README.zh-CN.md)

#### Tombstone 文件默认将被写入到 Context#getFilesDir() + "/tombstones" 目录。（通常在： /data/data/PACKAGE_NAME/files/tombstones）
处理思路：
- app启动后10秒，开始逐个上传tombstones目录下的崩溃文件，上传成功一个删除一个。
- app启动后10秒内发生的崩溃，在callback中立刻尝试投递，投递成功则删除。

