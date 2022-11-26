package com.imooc.router.gradle

import groovy.json.JsonSlurper
import org.gradle.api.Plugin
import org.gradle.api.Project

class RouterPlugin implements Plugin<Project> {

    // 实现apply方法，注入插件的逻辑
    void apply(Project project) {
        println("I am from RouterPlugin, apply from ${project.name}")

        // 1. 自动帮助用户传递路径参数到注解处理器中
        if (project.extensions.findByName("kapt") != null) {
            project.extensions.findByName("kapt").arguments {
                arg("root_project_dir", project.rootProject.projectDir.absolutePath)
            }
        }

        // 2. 实现旧的构建产物的自动清理
        project.clean.doFirst {
            // 删除 上一次构建生成的 router_mapping目录
            File routerMappingDir = new File(project.rootProject.projectDir, "router_mapping")
            if (routerMappingDir.exists()) {
                routerMappingDir.deleteDir()
            }
        }

        // 2. 注册 extension
        project.getExtensions().add("router", RouterExtension)

        // 4. 获取 Extension
        project.afterEvaluate {
            RouterExtension extension = project["router"]
            println("用户配置的 值为 ${extension.wikiDir}")
            // 第二种写法，获取配置的值
//            println("用户配置的 值为 ${extension.getProperty("wikiDir")}")

            // 3. 在javac任务 (compileDebugJavaWithJavac) 后，汇总生成文档
            project.tasks.findAll { task -> task.name.startsWith('compile') && task.name.endsWith('JavaWithJavac') }
                    .each { task ->
                        task.doLast {
                            File routerMappingDir = new File(project.rootProject.projectDir, "router_mapping")
                            if (!routerMappingDir.exists()) {
                                return
                            }

                            File[] allChildFiles = routerMappingDir.listFiles()
                            if (allChildFiles.length < 1) {
                                return
                            }

                            StringBuilder markdownBuilder = new StringBuilder()
                            markdownBuilder.append("# 页面文档\n\n")
                            allChildFiles.each { child ->
                                if (child.name.endsWith(".json")) {
                                    JsonSlurper jsonSlurper = new JsonSlurper()
                                    def content = jsonSlurper.parse(child)
                                    content.each { innerContent ->
                                        def url = innerContent['url']
                                        def description = innerContent['description']
                                        def realPath = innerContent['realPath']

                                        markdownBuilder.append("## $description \n")
                                        markdownBuilder.append("- url: $url \n")
                                        markdownBuilder.append("- realPath: $realPath \n\n")
                                    }
                                }
                            }

                            File wikiFileDir = new File(extension.wikiDir)
                            if (!wikiFileDir.exists()) {
                                wikiFileDir.mkdir()
                            }

                            File wikiFile = new File(wikiFileDir, "页面文档.md")
                            if (wikiFile.exists()) {
                                wikiFile.delete()
                            }

                            wikiFile.write(markdownBuilder.toString())
                        }
                    }
        }
    }

}
