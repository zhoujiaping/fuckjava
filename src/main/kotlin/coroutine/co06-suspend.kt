package coroutine

import kotlinx.coroutines.*

/*
*自定义 suspend 函数
如果你的某个函数比较耗时，也就是要等的操作，那就把它写成 suspend 函数。这就是原则。
耗时操作一般分为两类：I/O 操作和 CPU 计算工作。
比如文件的读写、网络交互、图片的模糊处理，都是耗时的，通通可以把它们写进 suspend 函数里。
另外这个「耗时」还有一种特殊情况，就是这件事本身做起来并不慢，但它需要等待，比如 5 秒钟之后再做这个操作。
这种也是 suspend 函数的应用场景。

suspend函数：内置suspend函数如delay，自定义suspend函数

自定义挂起函数，需要在该函数内部直接/间接调用到某一个自带的挂起函数才行【例如withContext()】
* 使用 withContext 函数可以切换到指定的线程，并在闭包内的逻辑执行结束后，自动把线程切换回上下文继续执行
* */
fun main() = runBlocking {
    launch { doWorld() }
    println("Hello,")
}

// 这是你的第一个挂起函数
suspend fun doWorld() {
    delay(1000L)
    println("World!")
}