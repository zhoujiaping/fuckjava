class DateRange(override val start: MyDate, override val endInclusive: MyDate)
    :ClosedRange<MyDate>,Iterable<MyDate> {
    override fun iterator(): Iterator<MyDate> {
        var curDate = start
        return object : Iterator<MyDate> {
            override fun next(): MyDate {
                val ret = curDate
                curDate = curDate.nextDay()
                return ret
            }
            override fun hasNext() = curDate <= endInclusive
        }
    }
}

fun checkInRange(date: MyDate, first: MyDate, last: MyDate): Boolean {
    return date in DateRange(first, last)
}
fun main() {
    println(checkInRange(MyDate(2021,1,25),MyDate(2021,1,24),MyDate(2021,1,26)))
}