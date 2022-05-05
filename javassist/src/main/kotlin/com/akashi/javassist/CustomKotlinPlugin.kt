package com.akashi.javassist

import com.akashi.javassist.transforms.CustomTransform
import com.android.build.gradle.AppExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

class CustomKotlinPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        println("-----Akashi Kotlin Plugin------")
        project.extensions.getByType(AppExtension::class.java)
            .registerTransform(CustomTransform(project))
    }
}