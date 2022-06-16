package com.akashi.plugin;

import com.akashi.plugin.bean.HelloKotlinExtension
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.kotlin.dsl.create
import java.util.*

/**
 * 自定义插件
 * 自定义extension
 * 自定义gradle task，输出extension信息
 */
class HelloKotlinPlugin : Plugin<Project> {

    override fun apply(project: Project): Unit = project.run {
        println("---- Akashi Kotlin Plugin v1 ----")

        val hkExt = project.extensions.create<HelloKotlinExtension>("HelloKotlinExtension")
        project.extensions.add("hello", hkExt)

        // 注册task到gradle
        tasks.register("hello") {
            doLast {
                println("==> ${hkExt.greeting.capitalize(Locale.ROOT)}, ${hkExt.name.capitalize(Locale.ROOT)}!")
            }
        }
    }
}
