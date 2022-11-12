package com.imooc.router.gradle


import org.gradle.api.Plugin
import org.gradle.api.Project

class RouterPlugin implements Plugin<Project> {

    // 实现apply方法，注入插件的逻辑
    void apply(Project project) {
        println("I am from plugin ${project.name}")
        // 2. 注册 extension
        project.getExtensions().add("router", RouterExtension)
        // 4. 获取 Extension
        project.afterEvaluate {
            RouterExtension extension = project["router"]
            println("用户配置的 值为 ${extension.wikiDir}")
            // 第二种写法，获取配置的值
            println("用户配置的 值为 ${extension.getProperty("wikiDir")}")
        }
    }

}
