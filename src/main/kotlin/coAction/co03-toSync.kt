package coAction

import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import mylib.info
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import kotlin.system.measureTimeMillis
/**回调风格转同步风格*/
fun main() {
    val time = measureTimeMillis {
        runBlocking{
            val defer = async{
                mockCallback()
            }
            val res = defer.await()
            println("res=$res")
        }
    }
    println("cost:$time")
}
suspend fun mockCallback()=suspendCoroutine<String> {
        Bus.info("...")
        Bus.subscribe("hello"){
            println("kitty")
            it.resume("kitty")
        }
        Bus.publish("hello")
}
/**自定义事件总线，为了模拟回调
 * */
class Bus{
    companion object Bus{
        private val handlers = mutableMapOf<String,MutableList<()->Unit>>()
        fun subscribe(event:String, handler:()->Unit){
            if(handlers[event]==null){
                handlers[event] = mutableListOf()
            }
            handlers[event]!!.add(handler)
        }
        fun publish(event:String){
            handlers[event]!!.forEach {
                it()
            }
        }
    }
}