package koans

fun iterateOverDateRange(firstDate: MyDate, secondDate: MyDate, handler: (MyDate) -> Unit) {
    for (date in firstDate..secondDate) {
        handler(date)
    }
}
fun main() {
    iterateOverDateRange(MyDate(2021,1,20),
        MyDate(2021,1,25)
    ) {println(it.dayOfMonth)}
}