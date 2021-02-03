package coroutine
import kotlinx.coroutines.*
/*
* 协程的取消是 协作 的。一段协程代码必须协作才能被取消。
* 所有 kotlinx.coroutines 中的挂起函数都是 可被取消的 。
* 它们检查协程的取消， 并在取消时抛出 CancellationException。
* 然而，如果协程正在执行计算任务，并且没有检查取消的话，那么它是不能被取消的
*
* 类似于线程的interrupt
* */
suspend fun main() {
    val job = GlobalScope.launch {
        repeat(1000) { i ->
            println("job: I'm sleeping $i ...")
            delay(500L)
        }
    }
    delay(1300L) // 延迟一段时间
    println("main: I'm tired of waiting!")
    job.cancel() // 取消该作业
    job.join() // 等待作业执行结束
    println("main: Now I can quit.")
}