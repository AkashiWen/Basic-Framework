# MVP基础架构探索实现

## 1. 实现线索

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

### 2.2 利用Lifecycle感知生命周期

- 被观察者：实现LifecycleOwner
    - Activity、Fragment
- 观察者：继承LifecycleObserver
    - 各种Presenter

### 2.2 总线 代替接口回调传输数据 Model -> Presenter

### 2.3 对象注入 Koin dagger2 Hlit 等

### 2.4 组件化

### 2.5 gradle git

### 2.6 插件化、热修复