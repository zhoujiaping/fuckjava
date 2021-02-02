package koans

import TimeInterval.*

operator fun MyDate.plus(timeInterval: TimeInterval): MyDate = addTimeIntervals(timeInterval,1)
operator fun MyDate.plus(timeIntervals: TimeIntervals): MyDate = addTimeIntervals(timeIntervals.interval,timeIntervals.number)

fun task1(today: MyDate): MyDate {
    return today + YEAR + WEEK
}

fun task2(today: MyDate): MyDate {
    return today + YEAR*2 + WEEK*3 + DAY*5
}

class TimeIntervals(val interval:TimeInterval,val number:Int)
private operator fun TimeInterval.times(i: Int)=TimeIntervals(this,i)

fun main() {
    println(task2(MyDate(2021,1,25)))
}


