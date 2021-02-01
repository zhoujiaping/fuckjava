import java.util.*

fun <T> Collection<T>.partitionTo(truePart:MutableCollection<T>,falsePart:MutableCollection<T>,predicate: (T)->Boolean): Pair<MutableCollection<T>,MutableCollection<T>> {
    for(item in this){
        if(predicate(item)){
            truePart.add(item)
        }else{
            falsePart.add(item)
        }
    }
    return Pair(truePart,falsePart)
}

fun partitionWordsAndLines() {
    val (words, lines) = listOf("a", "a b", "c", "d e").
    partitionTo(ArrayList<String>(), ArrayList()) { s -> !s.contains(" ") }
    words == listOf("a", "c")
    lines == listOf("a b", "d e")
}

fun partitionLettersAndOtherSymbols() {
    val (letters, other) = setOf('a', '%', 'r', '}').
    partitionTo(HashSet<Char>(), HashSet()) { c -> c in 'a'..'z' || c in 'A'..'Z'}
    letters == setOf('a', 'r')
    other == setOf('%', '}')
}