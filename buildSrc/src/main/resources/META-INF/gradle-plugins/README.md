### 空说明

使用kotlin-dsl注册 可以避免在这里静态注册创建

```kotlin
gradlePlugin {
    plugins {
        register("hello-kotlin-plugin") {
            // 如何调用：plugin { id: 'plugin.akashi.hello-kotlin' }
            id = "plugin.akashi.hello-kotlin"
            implementationClass = "com.akashi.plugin.HelloKotlinPlugin"
        }
    }
}

```