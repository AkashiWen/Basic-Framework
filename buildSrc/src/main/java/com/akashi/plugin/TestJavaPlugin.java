package com.akashi.plugin;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

/**
 * buildSrc下的build.gradle优先于根目录下的build.gradle执行
 * 基于这种情况
 * 配置Kotlin写法有坑，默认使用Java
 */
public class TestJavaPlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        System.out.println("---- Akashi Java Plugin ----");
    }
}
