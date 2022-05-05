package com.akashi.javassist

import org.gradle.api.Plugin
import org.gradle.api.Project;

class ModifyPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        println("-----Akashi Plugin------")
    }
}
