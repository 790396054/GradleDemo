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


字节码插桩技术 用 ASM 生成字节码，生成的时机在 transform 中。
transform 是 AGP（Android Gradle Plugin）中提供的接口，实现这个接口就可以在编译为 class 之后，dex 之前，调用
这个接口的实现。
例子 demo：buildSrc RouterMappingTransform

查看 APP 的依赖关系
./gradlew :app:dependencies --configuration debugCompileClasspath

查看 gradle 执行任务的性能分析。--offline 是离线模式，不拉取网络的依赖库。--rerun-tasks 所以的 task 执行一遍，不用缓存，--profile 性能统计，生成分析报告。路径 GradleDemo/build/reports/profile/profile-2022-11-26-12-04-27.html
./gradlew clean assembleDebug --offline --rerun-tasks --profile   
