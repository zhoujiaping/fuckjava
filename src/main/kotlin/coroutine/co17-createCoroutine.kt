package coroutine

import kotlin.coroutines.*

//https://blog.csdn.net/qq923201817/article/details/113619938
fun main() {
    val continuation = suspend {
        println("Coroutine Start.")
        "return value."
    }.createCoroutine(object : Continuation<String> {
        override val context: CoroutineContext
            get() = EmptyCoroutineContext

        override fun resumeWith(result: Result<String>) {
            println("Coroutine End : $result")
        }
    })
    continuation.resume(Unit)   // 启动协程
}

