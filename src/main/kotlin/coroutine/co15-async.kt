package coroutine

import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

/*
* 在概念上，async 就类似于 launch。它启动了一个单独的协程，这是一个轻量级的线程并与其它所有的协程一起并发的工作。
* 不同之处在于 launch 返回一个 Job 并且不附带任何结果值，而 async 返回一个 Deferred —— 一个轻量级的非阻塞 future，
* 这代表了一个将会在稍后提供结果的 promise。
* 你可以使用 .await() 在一个延期的值上得到它的最终结果， 但是 Deferred 也是一个 Job，所以如果需要的话，你可以取消它。
* */
suspend fun main() {
    withAsync()
}
suspend fun withoutAsync() {
    val time = measureTimeMillis {
        val one = doSomethingUsefulOne()
        val two = doSomethingUsefulTwo()
        println("The answer is ${one + two}")
    }

    println("Completed in $time ms")
}
suspend fun withAsync(){
    val time = measureTimeMillis {
        val one = GlobalScope.async { doSomethingUsefulOne() }
        val two = GlobalScope.async { doSomethingUsefulTwo() }
        println("The answer is ${one.await() + two.await()}")
    }
    println("Completed in $time ms")
}
/*
* 惰性启动的 async
可选的，async 可以通过将 start 参数设置为 CoroutineStart.LAZY 而变为惰性的。
* 在这个模式下，只有结果通过 await 获取的时候协程才会启动，或者在 Job 的 start 函数调用的时候。
*
* 注意，如果我们只是在 println 中调用 await，而没有在单独的协程中调用 start，这将会导致顺序行为，
* 直到 await 启动该协程 执行并等待至它结束，这并不是惰性的预期用例。
* 在计算一个值涉及挂起函数时，这个 async(start = CoroutineStart.LAZY) 的用例用于替代标准库中的 lazy 函数。
* */
suspend fun lazyAsync(){
    val time = measureTimeMillis {
        val one = GlobalScope.async(start = CoroutineStart.LAZY) { doSomethingUsefulOne() }
        val two = GlobalScope.async(start = CoroutineStart.LAZY) { doSomethingUsefulTwo() }
        // 执行一些计算
        one.start() // 启动第一个
        two.start() // 启动第二个
        println("The answer is ${one.await() + two.await()}")
    }
    println("Completed in $time ms")
}

suspend fun doSomethingUsefulOne(): Int {
    delay(1000L) // 假设我们在这里做了一些有用的事
    return 13
}

suspend fun doSomethingUsefulTwo(): Int {
    delay(1000L) // 假设我们在这里也做了一些有用的事
    return 29
}
