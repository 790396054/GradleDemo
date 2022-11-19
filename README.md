# GradleDemo

Gradle 练习项目

包括 APT 注解处理器的演示，插件的发布与使用，本地插件的使用。

注解处理器需要 annotationProcessor 或者 kapt 使用。

插件的使用：classpath "com.android.tools.build:gradle:4.2.2"
            apply plugin: 'com.android.application'

插件的发布脚本：maven_publish.gradle，发布到本地项目的 repo 目录下

插件的发布：
./graldew : router-annotations:uploadArchives
./graldew : router-processor:uploadArchives

本地插件的使用：


