## 这是什么jb?

Mybatis-plus 的 代码生成器 的 **GUI**版

pom依赖里的 **velocity** 和 **spring** 是为了**兼容**

## 能干什么?

生成代码,do the same as the **`mybatis-plus-generator`**

还有部分功能未实现


## 一些细节问题

暂时没有做全角空格之类的清除，因为是 programmer-oriented，就懒了
打印日志问题，因为 `mp-generator` 里的代码异常是 `ex.printStack()` 或者 `System.err.println()`，
而本程序 TextArea是重定向了System.out流，但 **日志文件** 是 logback生成的 并不识别得到 `mp-generator` 的红字输出。
故 `mp-code-generator-gui.log` 和 GUI窗里的内容有所不同。

