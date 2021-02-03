package coroutine

import kotlinx.coroutines.*
/*
* 全局协程像守护线程
以下代码在 GlobalScope 中启动了一个长期运行的协程，该协程每秒输出“I'm sleeping”两次，之后在主函数中延迟一段时间后返回
* */
suspend fun main(){
    GlobalScope.launch {
        repeat(1000) { i ->
            println("I'm sleeping $i ...")
            delay(500L)
        }
    }
    delay(1300L) // 在延迟后退出
}