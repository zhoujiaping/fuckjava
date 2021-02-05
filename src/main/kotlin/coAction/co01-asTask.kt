package coAction

import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

/**当成简单的任务管理器/调度器来用
 * 安排两个任务，可以并行执行或者串行执行
 * */
fun main() {
    val time = measureTimeMillis {
        runBlocking{
            launch{
                task1()
            }
            launch{
                task2()
            }
        }
    }
    println("cost:$time")
}
suspend fun task1(){
    delay(1000)
    println("task1")
}
suspend fun task2(){
    delay(2000)
    println("task2")
}
/*
* output:
task1
task2
cost:2268
* */