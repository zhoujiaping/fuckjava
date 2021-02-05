package coroutine

import kotlinx.coroutines.*
import java.time.LocalTime
import kotlin.system.measureTimeMillis

fun main()  {
    val time = measureTimeMillis{
        runBlocking{
            println("Hello, world!")
            println(LocalTime.now())
            launch{
                delay(2000)
                yield()
                delay(1500)
            }
            launch{
                delay(3500)
            }
            println(LocalTime.now())
        }
    }
    println("cost:$time")
}