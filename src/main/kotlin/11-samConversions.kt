import java.util.*

fun getList11(): List<Int> {
    val arrayList = arrayListOf(1, 5, 2)
    arrayList.sortWith(Comparator { x, y -> x-y })
    //Collections.sort(arrayList){x,y->x-y}

    return arrayList
}