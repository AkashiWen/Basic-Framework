package com.akashi.javassist.transforms

import com.android.build.api.transform.Format
import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformInvocation
import com.android.build.gradle.internal.pipeline.TransformManager
import com.android.utils.FileUtils
import org.gradle.api.Project

class CustomTransform(project: Project) : Transform() {

    override fun transform(transformInvocation: TransformInvocation?) {
        super.transform(transformInvocation)
        // 1. 拿到输入
        transformInvocation?.inputs?.forEach {
            // class --> 文件夹
            // jar
            // --> app/build/../.dex
            it.directoryInputs.forEach { dir ->
                // 2. 查询输出的文件夹
                val dest = transformInvocation.outputProvider.getContentLocation(
                    dir.name,
                    dir.contentTypes,
                    dir.scopes,
                    Format.DIRECTORY
                )
                println("=====Dir Name====== $dest")
                FileUtils.copyDirectory(dir.file, dest)
            }

            it.jarInputs.forEach { jar ->
                // 2. 查询输出的文件夹
                val dest = transformInvocation.outputProvider.getContentLocation(
                    jar.name,
                    jar.contentTypes,
                    jar.scopes,
                    Format.JAR
                )
                println("=====Jar Name====== $dest")
                FileUtils.copyFile(jar.file, dest)
            }
        }

        // 3. 利用ClassPool、javassist`开始修改.class

    }

    override fun getName(): String = "Akashi"

    override fun getInputTypes(): MutableSet<QualifiedContent.ContentType> {
        return TransformManager.CONTENT_CLASS
    }

    override fun getScopes(): MutableSet<in QualifiedContent.Scope> {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    override fun isIncremental(): Boolean = false
}