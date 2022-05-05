package com.akashi.javassist

import org.gradle.api.Plugin
import org.gradle.api.Project

class CustomKotlinPlugin : Plugin<Project> {
    override fun apply(p0: Project) {
        println("-----Akashi Kotlin Plugin------")
    }
}