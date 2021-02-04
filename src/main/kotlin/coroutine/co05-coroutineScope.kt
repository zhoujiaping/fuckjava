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

 coroutineScope:挂起当前协程，当前协程所在线程可以执行其他任务
 */
fun main() = runBlocking { // this: CoroutineScope //协程1
    launch {//协程2
        delay(200L)
        println("Task from runBlocking")
    }
    println("Hello, + ${Thread.currentThread().name}")

    coroutineScope { // 创建一个协程作用域
        println("Hello, + ${Thread.currentThread().name}")

        launch {//协程3
            delay(500L)
            println("Task from nested launch")
        }
        delay(100L)
        println("Task from coroutine scope") // 这一行会在内嵌 launch 之前输出
    }

    println("Coroutine scope is over") // 这一行在内嵌 launch 执行完毕后才输出
}
/*
输出结果：
Task from coroutine scope
Task from runBlocking
Task from nested launch
Coroutine scope is over

runBlocking创建了一个协程，并且在该协程执行完之前会阻塞当前线程。
coroutineScope并没有创建协程，它创建了一个协程作用域。里面的普通代码和外面的代码实际上是按顺序执行的。
所以println("Task from coroutine scope")在println("Coroutine scope is over")之前。
coroutineScope创建了协程作用域，只有等它里面的协程执行完它才算执行完。

当launch, async或runBlocking开启新协程的时候, 它们自动创建相应的scope.
所有的这些方法都有一个带receiver的lambda参数, 默认的receiver类型是CoroutineScope.

* */
