package koans
import java.util.*

fun getList11(): List<Int> {
    val arrayList = arrayListOf(1, 5, 2)
    arrayList.sortWith(Comparator { x, y -> y-x })
    //Collections.sort(arrayList){x,y->x-y}
    return arrayList
}