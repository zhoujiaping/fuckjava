package koans

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int) : Comparable<MyDate> {
    /*override fun compareTo(other: MyDate): Int {
        if(this.year!=other.year){
            return this.year-other.year
        }
        if(this.month!=other.month){
            return this.month-other.month
        }
        return this.dayOfMonth-other.dayOfMonth
    }*/
    override fun compareTo(other: MyDate) = when {
        year != other.year -> year - other.year
        month != other.month -> month - other.month
        else -> dayOfMonth - other.dayOfMonth
    }

}

fun compare(date1: MyDate, date2: MyDate) = date1 < date2