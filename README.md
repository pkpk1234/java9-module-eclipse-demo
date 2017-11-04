# 使用eclipse编写Java9模块

标签（空格分隔）： Java9  模块 Jigsaw

---

本文记录用eclipse开发最简单的Java9模块的过程。
Java9模块的其他详细信息后续再写。
完成后的工程结构如下：
![project-structure][1]

## 编写对外的功能
我们将创建名为com.example.hello的模块。
新建java工程hello-module，注意选择Java版本为java9。
![创建工程][2]

创建模块文件夹，根据非强制的约定，模块文件夹的名称和模块的名称相同。因为需要编译模块，所以可以直接将模块文件夹创建为eclipse的source folder。
![source-folder][3]

![souce-folder2][4]

创建模块描述文件module-info.java，低版本的eclipse会认为module-info.java不是合法的java文件名，所以此处直接创建module-info.java的File。
![new-mod-file1][5]

![new-mod-file2][6]
在module-info.java中，我们将模块暴露出去。如下：
```java
module com.example.hello {
	exports com.example.hello;
}
```
编写类HelloModule，里面只有一个main方法，提供给外部调用。如下：
```java
public class HelloModule {
	public static void main(String[] args) {
		Class<HelloModule> clazz = HelloModule.class;
		Module module = clazz.getModule();
		String moduleName = module.getName();
		System.out.println("Hello from module: "+moduleName);
	}
}
```
这里使用了Java9中新增加的Module类，获取到模块的名称并输出。

## 编写调用方
我们将创建com.example.requirer的模块，此模块依赖了模块com.example.hello。
创建Java工程hello-requirer，注意选择Java版本为java9。
使用创建com.example.hello模块类似的方式，创建source folder。
编写module-info.java，指定依赖。如下：
```java
module com.example.requirer {
	requires com.example.hello;
}
```
此时eclipse编译会抛出异常，因为找不到名为com.example.hello的模块。
![module-notfound][7]
eclipse中可以通过添加module path解决。
打开工程hello-requirer的properties界面
![此处输入图片的描述][8]
在Java Build Path处，添加module path为工程hello-module
![add-module][9]

调用com.example.hello模块中方法，如下：
```java
public class RequirerMain {
	public static void main(String[] args) {
		HelloModule.main(args);
		System.out.println("Requirer main finished.");
	}
}
```
运行输出如下：
Hello from module: com.example.hello
Requirer main finished.

完整代码：https://github.com/pkpk1234/java9-module-eclipse-demo

  [1]: https://ip.freep.cn/593396/java9%E6%A8%A1%E5%9D%97/hello-module/project-structure.jpg
  [2]: https://ip.freep.cn/593396/java9%E6%A8%A1%E5%9D%97/hello-module/create-project-1.jpg
  [3]: https://ip.freep.cn/593396/java9%E6%A8%A1%E5%9D%97/hello-module/source-folder.jpg
  [4]: https://ip.freep.cn/593396/java9%E6%A8%A1%E5%9D%97/hello-module/souce-folder2.jpg
  [5]: https://ip.freep.cn/593396/java9%E6%A8%A1%E5%9D%97/hello-module/new-mod-file1.jpg
  [6]: https://ip.freep.cn/593396/java9%E6%A8%A1%E5%9D%97/hello-module/Jietu20171104-200735.jpg
  [7]: https://ip.freep.cn/593396/java9%E6%A8%A1%E5%9D%97/hello-module/module-notfound.jpg
  [8]: https://ip.freep.cn/593396/java9%E6%A8%A1%E5%9D%97/hello-module/project-properties.jpg
  [9]: https://ip.freep.cn/593396/java9%E6%A8%A1%E5%9D%97/hello-module/add-module.jpg
