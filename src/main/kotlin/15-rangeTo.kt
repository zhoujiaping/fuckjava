
operator fun MyDate.rangeTo(other: MyDate) = DateRange(this,other)

fun checkInRange15(date: MyDate, first: MyDate, last: MyDate): Boolean {
    return date in first..last
}