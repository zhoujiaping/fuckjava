kotlin coroutine
什么是协程
    用户级线程，在系统线程上运行
    协程的调度是应用层完成的，只有一个协程主动让出cpu执行权，其他协程才有机会执行
    线程调度是抢占式调度
为什么需要协程
    解决线程切换开销大的问题
    线程并发需要锁控制的问题
    （如果两个协程跑在不同的线程上，并且修改相同资源，也是需要锁控制的）
    解决回调黑洞
    任务调度
协程的实现原理
    有栈协程（Stackful Coroutine）
    无栈协程（Stackless Coroutine）
    解决回调黑洞需要配合cps（continuation passing style）
    同一个线程上协程的切换，就是该线程在自己的调用栈上切换

kotlin协程相关概念
CoroutineScope
    协程的作用域，用来做协程的管理。一个协程作用域可以管理域内的所有协程。
    通过结构化编程，方便管理协程之间的生命周期关系。
CoroutineContext
    协程的上下文。包含用户定义的一些数据集合，这些数据与协程密切相关。
    协程上下文包含当前协程scope的信息，比如的Job,ContinuationInterceptor,CoroutineName和CoroutineId。
Continuation
    cps的continuation，用来实现回调代码风格转同步代码风格
suspend
    用作修饰会被暂停的函数，被标记为suspend的函数只能运行在协程或者其他suspend函数当中。
Job、Deferred
    提供了管理协程的一套api，比如启动、取消、join、获取激活状态等。
ContinuationInterceptor
    continuation拦截器，可以用来对协程进行拦截。
CoroutineDispatcher
    协程分发器，用来关联协程和执行协程的线程。
Channel、Flow、CoroutineScheduler、Select、Semaphore、EventLoop等
    
理解：
如果只是解决线程切换的问题，其实不需要协程，需要的是任务调度
（创建各种task，然后分配在线程池上，各线程上跑分别跑一些task）。
个人认为协程的重点在于解决回调黑洞这样的问题。
rxjava实际上就提供了一套任务调度的方案。
关于协程处理共享资源不需要锁，将相关的task安排到同一个线程执行，一样不需要锁。
（搞一个线程池group，某些线程池里面常驻一个线程，那么安排在该线程池中的任务，就是单线程的了）
当然协程可以实现同一个线程上的协程之间切换，但是如果用线程池group上单个线程的方案，并不能实现该效果。

综上，协程的主要作用有
协程调度（任务调度），协程生命周期管理，协程异常处理等
配合cps实现异步代码风格转同步代码风格
同一个线程上的协程之间可以相互协作，交叉执行
（用多线程跑任务做任务间交叉执行会导致线程切换，
在单线程上跑多个任务，又无法做任务间交叉执行，除非实现自动任务分解）

如果一个协程中有个io操作，而这个io操作用的是bio，那么用协程也不能提升性能。
（把io操作放到任务中，丢到其他线程跑，一样不阻塞当前线程）
如果一个协程中有个io操作，而这个io操作用的是nio/aio，那么不用协程性能一样好。
（但是代码是异步风格，用协程+cps转为同步风格能提升可读性和简化异常处理）

为什么java一直不搞协程，就是上面的那些功能，要么有替代方案（即使没协程方案优雅），要么就不是必须的。
所以如果有人问你为什么要用协程，协程解决的各种问题，都有对应的传统方案?
答案是协程提供了更优雅更好用的方案，极大的方便了开发。
（不用自己搞任务调度，任务生命周期管理，任务异常管理，任务交叉执行等，比如通过结构化编程，实现任务编排）

kotlin原生coroutine
org.jetbrains.kotin:kotlin-stdlib:1.4.0
kotlin.coroutines.*

jetbrains自己开发的协程库
implementation("org.jetbrains.kotlinx","kotlinx-coroutines-core", "1.3.9")


文章

什么是协程？
https://zhuanlan.zhihu.com/p/172471249

Kotlin Coroutines不复杂, 我来帮你理一理
https://blog.csdn.net/weixin_45714179/article/details/103199536

Kotlin协程高级技巧和诀窍
https://blog.csdn.net/tigershin/article/details/86482808

探究高级的Kotlin Coroutines知识
https://www.cnblogs.com/mengdd/p/deep-explore-kotlin-coroutines.html

kotlin - Coroutine 协程
https://www.jianshu.com/p/76d2f47b900d

kotlin之协程(七),协程中relay、yield 区别
https://www.jianshu.com/p/402a69dbd66d

理解有栈无栈协程
https://zhuanlan.zhihu.com/p/65956116

深入理解Kotlin协程（一）——基本概念
https://blog.csdn.net/qq923201817/article/details/113619938

Kotlin1.3协程Api详解（CoroutineScope， CoroutineContext详解）
https://www.jianshu.com/p/0ecd895b1016

Kotlin异步转同步
https://blog.csdn.net/long8313002/article/details/109164146