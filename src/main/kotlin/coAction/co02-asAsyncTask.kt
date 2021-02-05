package coAction

import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

/**当成简单的任务管理器/调度器来用
 * 安排两个任务，并行执行
 * */
fun main() {
    val time = measureTimeMillis {
        runBlocking{
            val defer1 = async{
                task2_1()
            }
            val defer2 = async{
                task2_2()
            }
            val res1 = defer1.await()
            val res2 = defer2.await()
            println("res1=$res1,res2=$res2")
        }
    }
    println("cost:$time")
}
suspend fun task2_1():String{
    delay(1000)
    println("task1")
    return "t1"
}
suspend fun task2_2():String{
    delay(2000)
    println("task2")
    return "t2"
}
/*
* output:
task1
task2
res1=t1,res2=t2
cost:2345
* */