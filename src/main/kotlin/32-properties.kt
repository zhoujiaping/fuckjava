class PropertyExample() {
    var counter = 0
    var propertyWithCounter: Int? = null
        set(value){
            field = value
            counter++
        }
}

fun main() {
    val example = PropertyExample()
    example.propertyWithCounter = 1
}