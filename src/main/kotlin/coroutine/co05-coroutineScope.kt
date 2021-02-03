package coroutine

import kotlinx.coroutines.*

/**
 作用域构建器
除了由不同的构建器提供协程作用域之外，
还可以使用 coroutineScope 构建器声明自己的作用域。
它会创建一个协程作用域并且在所有已启动子协程执行完毕之前不会结束。

runBlocking 与 coroutineScope 可能看起来很类似，因为它们都会等待其协程体以及所有子协程结束。
主要区别在于，runBlocking 方法会阻塞当前线程来等待， 而 coroutineScope 只是挂起，会释放底层线程用于其他用途。
由于存在这点差异，runBlocking 是常规函数，而 coroutineScope 是挂起函数。

 runBlocking:挂起当前线程等待协程体执行结束
 coroutineScope:挂起当前协程，当前协程所在线程可以执行其他任务
 */
fun main() = runBlocking { // this: CoroutineScope
    launch {
        delay(200L)
        println("Task from runBlocking")
    }

    coroutineScope { // 创建一个协程作用域
        launch {
            delay(500L)
            println("Task from nested launch")
        }

        delay(100L)
        println("Task from coroutine scope") // 这一行会在内嵌 launch 之前输出
    }

    println("Coroutine scope is over") // 这一行在内嵌 launch 执行完毕后才输出
}