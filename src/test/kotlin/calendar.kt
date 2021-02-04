import java.time.DayOfWeek
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjusters
import java.util.*
fun LocalDate.toDate():Date=Date.from(this.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())
fun LocalDate.format(fmt:String):String=this.format(DateTimeFormatter.ofPattern(fmt))
fun main() {
   // days()
    //println()
    weeks()
}
fun days(){
    val dates = mutableListOf<LocalDate>()
    val beginDate = LocalDate.now().minusWeeks(3)
    var curDate = beginDate
    while(curDate.isBefore(fiveYearsLatter())){
        dates.add(curDate)
        curDate = curDate.plusDays(1)
    }
    println("-- days")
    println(dates.map{"\n('"+it.format("yyyyMMdd")+"','day')"}.joinToString(",","insert into t_calendar(date,type)values",";"))
}
fun weeks(){
    System.currentTimeMillis()
    val dates = mutableListOf<LocalDate>()
    val beginDate = LocalDate.now().minusWeeks(3).with(TemporalAdjusters.previous(DayOfWeek.SUNDAY))
    var curDate = beginDate
    while(curDate.isBefore(fiveYearsLatter())){
        dates.add(curDate)
        curDate = curDate.plusWeeks(1)
    }
    println("-- weeks")
    println(dates.map{"\n('"+it.format("yyyyMMdd")+"','week')"}.joinToString(",","insert into t_calendar(date,type)values",";"))
}
fun fiveYearsLatter():LocalDate=LocalDate.now().plusYears(5)
