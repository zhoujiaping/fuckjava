package koans

import java.util.HashMap

/**
fun buildString(build: StringBuilder.() -> Unit): String {
    val stringBuilder = StringBuilder()
    stringBuilder.build()
    return stringBuilder.toString()
}
val s = buildString {
    this.append("Numbers: ")
        for (i in 1..3) {
        // 'this' can be omitted
        append(i)
    }
}
s == "Numbers: 123"
 * */

fun usage(): Map<Int, String> {
    return buildMutableMap {
        put(0, "0")
        for (i in 1..10) {
            put(i, "$i")
        }
    }
}

fun buildMutableMap(build: MutableMap<Int, String>.() -> Unit): Map<Int, String> {
    val map = mutableMapOf<Int,String>()
    map.build()
    return map
}
