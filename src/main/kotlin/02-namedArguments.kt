fun joinOptions(options: Collection<String>) =
    options.joinToString(separator=",")
fun main() {
    println(joinOptions(listOf("a","b")))
}
