plugins {
    `kotlin-dsl`
}

repositories { // required by kotlin-dsl plugin dependencies
    mavenCentral()
}

gradlePlugin {
    plugins {
        register("hello-kotlin-plugin") {
            // 如何调用：plugin { id: 'plugin.akashi.hello-kotlin' }
            id = "plugin.akashi.hello-kotlin"
            implementationClass = "com.akashi.plugin.HelloKotlinPlugin"
        }
    }
}
