class LazyProperty34(private val initializer: () -> Int) {
    val lazyValue: Int by lazy(initializer)
}