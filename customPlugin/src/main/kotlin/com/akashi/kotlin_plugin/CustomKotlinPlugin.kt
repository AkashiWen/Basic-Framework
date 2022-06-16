package com.akashi.kotlin_plugin

import com.akashi.kotlin_plugin.route.MyAppExtension
import com.android.build.gradle.internal.dsl.ApplicationExtensionImpl
import org.gradle.api.Plugin
import org.gradle.api.Project

class CustomKotlinPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        println("----- applying Custom Kotlin Plugin------")

        // 模仿源码创建extension
//        project.extensions
//            .create(
//                "android11",
//                MyAppExtension::class.java
//            )

//        project.extensions.getByType(AppExtension::class.java)
//            .registerTransform(CustomTransform(project))
    }
}