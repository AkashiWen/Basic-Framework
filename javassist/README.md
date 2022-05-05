# javassit、美图热修复核心原理

OOP面向切面技术：AspectJ （.java - > .class）
字节码手术刀：javassit （.class -> .dex）
字节码插桩技术：ASM（.class -> .dex）


## 1. robus美团热修复实现原理

javassit，插件Groovy

- [Gradle7.0之后配置](https://docs.gradle.org/7.0/userguide/publishing_maven.html#publishing_maven)
- [maven-publish具体使用笔记](https://juejin.cn/post/7072611764222820366#heading-7)

效果：在每个方法里加条件判断和执行函数


### 1.1 流程

- 建立groovy module
    - 创建src/main/groovy/com.javassist/目录
    - 修改build.gradle
      ```groovy
      plugins {  
        id 'groovy'  
        id 'maven-publish'  
      }  
        
      java {  
        sourceCompatibility = JavaVersion.VERSION_1_7  
        targetCompatibility = JavaVersion.VERSION_1_7  
      }  
        
      dependencies {  
        implementation gradleApi()  
          implementation localGroovy()  
          implementation fileTree(dir: 'libs', includes: ['*.jar'])  
          implementation 'org.javassist:javassist:3.28.0-GA'  
          implementation 'com.android.tools.build:gradle:4.1.2'  
      }  
        
      publishing {  
        // TODO
      }
      ```
- 创建插件文件ModifyPlugin.groovy
  ```groovy
      class ModifyPlugin implements Plugin<Project> {  
          @Override  
          void apply(Project project) {  
              println("-----Akashi Plugin------")  
          }  
      }	
  ```
- 在src/main/resources/META-INF/gradle.plugins/下创建插件文件`com.javassist.properties`
  ```properties
  implementation-class=com.javassit.ModifyPlugin
  ```
- 让Groovy Module成为jar包，让其他工程引用
  ```groovy
  publishing {  
        publications {  
            maven(MavenPublication) {  
                // com.javassit:modify:1.0.0  
                groupId = 'com.javassist'  
                artifactId = 'modify'  
                version = '1.0.0'  
            }  
       }  
       repositories {  
            mavenLocal()//本地仓库(默认)  
            maven {  
                //本地maven地址配置(自定义，也可以是一个maven仓库地址)  
                url = uri("${rootProject.projectDir}/repo")  
            }  
       }
   }
  ```
- 执行publish打包
- 在其他Module中引入
  ```groovy
  apply plugin: 'com.javassist'
  ```

### 1.2 使用javassist之前 —— Transform

transform插入到.class -> .dex 之间

```kotlin
class CustomKotlinPlugin : Plugin<Project> {  
    override fun apply(project: Project) {  
        println("-----Akashi Kotlin Plugin------")  
        project.extensions.getByType(AppExtension::class.java)  
            .registerTransform(CustomTransform(project))  
    }  
}
```

```kotlin
class CustomTransform(project: Project) : Transform() {
	override fun transform(transformInvocation: TransformInvocation?) {  
	    super.transform(transformInvocation)
	    transformInvocation?.inputs?.forEach {
		    // 复制目录
		    it.directoryInputs.forEach { dir -> }
		    // 复制jar包
		    it.jarInputs.forEach { jar -> }
	    }
	    
    }
}
```


### 1.3 使用javassist —— ClassPool
修改.class文件
如为每个方法增加条件逻辑、增加运行耗时
