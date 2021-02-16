为什么项目叫做fuckjava？
因为访问www.fuckjava.com会跳到https://kotlinlang.org/

在线运行kotlin代码
https://try.kotlinlang.org/


gradle build文件中文乱码解决
解决方案：
1. 找到gradle的安装目录，进入到/bin的目录下面，找到gradle.bat或gradle文件
2. 编辑gradle.ba或gradlet文件，将DEFAULT_JVM_OPTS设置成DEFAULT_JVM_OPTS="-Dfile.encoding=UTF-8"，并保存。
3. 打开cmd，进入到具体的某个项目下面，执行gradlew clean build命令，还是会出现Java文件的中文乱码，因此，需要在这个命令后添加参数：gradlew clean build -Dfile.encoding=UTF-8。

