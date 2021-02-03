什么是协程？
https://zhuanlan.zhihu.com/p/172471249

kotlin coroutine
什么是协程
    用户级线程
    协程的调度是应用层完成的，只有一个协程主动让出cpu执行权，其他协程才有机会执行
    线程调度是抢占式调度
为什么需要协程
    解决线程切换开销大的问题，线程并发需要锁控制的问题，解决异步回调黑洞
协程的实现原理
    两类：有栈协程和无栈协程
    一个协程就是一个状态机
    解决回调黑洞需要配合cps（continuation passing style）
协程的使用场景
    异步操作
协程的基本操作，包括创建、启动、暂停和继续
CoroutineContext
    协程的上下文
Continuation
    cps的continuation
suspend
    用作修饰会被暂停的函数，被标记为 suspend 的函数只能运行在协程或者其他 suspend 函数当中。
如何使用
    

文章
Kotlin Coroutines不复杂, 我来帮你理一理
https://blog.csdn.net/weixin_45714179/article/details/103199536

Kotlin协程高级技巧和诀窍
https://blog.csdn.net/tigershin/article/details/86482808

探究高级的Kotlin Coroutines知识
https://www.cnblogs.com/mengdd/p/deep-explore-kotlin-coroutines.html

kotlin - Coroutine 协程
https://www.jianshu.com/p/76d2f47b900d

