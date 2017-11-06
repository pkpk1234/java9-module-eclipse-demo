# 使用命令行编译和运行Java9模块hello world

标签（空格分隔）： Java Java9

---

[上一篇文章][1]中使用Eclipse编写和运行了最简单的Java9模块。
此处使用命令javac、jar对Java9模块进行编译、打包，并使用命令java运行。
拷贝[上一篇文章中的代码][2]，并将目录结构修改如下：

![此处输入图片的描述][3]

其中的java文件全部拷贝自上一篇文章，没有任何改动。
新增了mods目录，用于存放编译后的模块class文件。
新增了mlib目录，用于存放打包后的模块jar文件。

注意：如果是在windows上，需要将 / 修改为 \ 。

## 编译com.exmaple.hello模块
运行命令 ~~javac -d mods/com.example.hello hello/com.example.hello/module-info.java hello/com.example.hello/com/example/hello/HelloModule.java~~

javac -encoding "UTF-8" -d mods --module-source-path hello hello/com.example.hello/module-info.java hello/com.example.hello/com/example/hello/HelloModule.java

用-d指定编译目的地为mods+模块名。
编译为模块时需要指定模块描述文件module-info.java。
参数--module-source-path指定了模块源码路径。
编译完成后，目录结构如下：

![file-tree-02][4]

注意mods下面创建了目录com.example.hello，编译后的com.exmaple.hello模块的class文件即位于该目录下。

## 编译com.example.requiere模块
运行命令 ~~javac -d mods/com.example.requirer --module-path mods  requirer/com.example.requirer/module-info.java requirer/com.example.requirer/com/example/requiere/RequirerMain.java~~

javac -encoding "UTF-8" -d mods --module-path mods --module-source-path requirer requirer/com.example.requirer/module-info.java requirer/com.example.requirer/com/example/requiere/RequirerMain.java

注意其中的--module-path参数指定了依赖的模块com.exmaple.hello位于目录mods下。
如果不添加此参数，javac会抛出错误: 找不到模块: com.example.hello。

![module-notfound][5]

编译完成后，目录结构如下：

![file-tree-03][6]

注意mods下面创建了目录com.example.requiere，编译后的com.example.requiere模块的class文件即位于该目录下。

## 运行
运行命令java --module-path mods -m com.example.requirer/com.example.requiere.RequirerMain

注意：
使用参数--module-path mods指定了模块位于目录mods下。

使用-m com.example.requirer/com.example.requiere.RequirerMain指定了运行模块com.example.requirer中的类com.example.requiere.RequirerMain中的main函数。

## 打包
首先打包模块com.example.hello为com.example.hello.jar，命令如下：
jar --create --file=mlib/com.example.hello.jar -C mods/com.example.hello .

参数--file=mlib/com.example.hello.jar指定了jar的路径。
参数-C指定了jar包要包含的目录，此处为模块的class路径。

然后打包模块com.example.requiere为com.example.requiere.jar，命令如下：

jar --create --file=mlib/com.example.requirer.jar --main-class=com.example.requiere.RequirerMain -C mods/com.example.requirer .

参数-main-class指定了jar包的Main-Class，即 java -jar com.example.requirer.jar时会执行com.example.requiere.RequirerMain中的main函数。
打包完成后，目录结构如下：

![file-tree-04][7]

运行命令：java --module-path mlib -m com.example.requirer
~~但是遇到了异常，暂时还未解决~~

![exception][8]
更新：Java9模块不允许同名的包存在于多个模块~~jar包~~中，遇到这样的场景，要不改包名，要不整合为一个模块。F-U-C-K

代码正常运行，如下：
![run-ok][9]

完整代码：https://github.com/pkpk1234/java9-module-cmd-demo




  [1]: https://zhuanlan.zhihu.com/p/30743052
  [2]: https://github.com/pkpk1234/java9-module-eclipse-demo
  [3]: https://ip.freep.cn/593396/java9%E6%A8%A1%E5%9D%97/hello-module-cmd/file-tree.jpg
  [4]: https://ip.freep.cn/593396/java9%E6%A8%A1%E5%9D%97/hello-module-cmd/file-tree-02.jpg
  [5]: https://ip.freep.cn/593396/java9%E6%A8%A1%E5%9D%97/hello-module-cmd/module-notfound.jpg
  [6]: https://ip.freep.cn/593396/java9%E6%A8%A1%E5%9D%97/hello-module-cmd/file-tree-03.jpg
  [7]: https://ip.freep.cn/593396/java9%E6%A8%A1%E5%9D%97/hello-module-cmd/file-tree-04.jpg
  [8]: https://ip.freep.cn/593396/java9%E6%A8%A1%E5%9D%97/hello-module-cmd/exception.jpg
  [9]: https://ip.freep.cn/593396/java9%E6%A8%A1%E5%9D%97/hello-module-cmd/run-ok.jpg
