package coroutine

import kotlinx.coroutines.*

/*
*  调用了 runBlocking 的主线程会一直 阻塞 直到 runBlocking 内部的协程执行完毕。
* */
fun main() {
    GlobalScope.launch { // 在后台启动一个新的协程并继续
        delay(1000L)
        println("World!")
    }
    println("Hello,") // 主线程中的代码会立即执行
    runBlocking {     // 但是这个表达式阻塞了主线程
        delay(2000L)  // ……我们延迟 2 秒来保证 JVM 的存活
    }
}
