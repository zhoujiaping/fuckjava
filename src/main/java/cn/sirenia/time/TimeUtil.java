package cn.sirenia.time;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import cn.sirenia.$;

public class TimeUtil {
    public static final String DEFAULT_DATE_PATTERN = "uuuu-MM-dd";
    public static final String DEFAULT_DATE_TIME_PATTERN = "uuuu-MM-dd HH:mm:ss";
    public static final String DEFAULT_TIME_PATTERN = "HH:mm:ss";
    //format
    public static String fmt(LocalDate date){
        return date.format(DateTimeFormatter.ofPattern(DEFAULT_DATE_PATTERN));
    }
    public static String fmt(LocalDate date,String pattern){
        return date.format(DateTimeFormatter.ofPattern(pattern));
    }
    public static String fmt(LocalDateTime date){
        return date.format(DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_PATTERN));
    }
    public static String fmt(LocalDateTime date, String pattern){
        return date.format(DateTimeFormatter.ofPattern(pattern));
    }
    public static String fmt(Date date){
        return date.toInstant().atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_PATTERN));
    }
    public static String fmt(Date date, String pattern){
        return date.toInstant().atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern(pattern));
    }
    //toLocalDate
    public static LocalDate toLocalDate(String date){
        return LocalDate.parse(date,DateTimeFormatter.ofPattern(DEFAULT_DATE_PATTERN));
    }
    public static LocalDate toLocalDate(String date,String pattern){
        return LocalDate.parse(date,DateTimeFormatter.ofPattern(pattern));
    }
    public static LocalDate toLocalDate(Date date){
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
    //toLocalDateTime
    public static LocalDateTime toLocalDateTime(String date){
        return LocalDateTime.parse(date,DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_PATTERN));
    }
    public static LocalDateTime toLocalDateTime(String date,String pattern){
        return LocalDateTime.parse(date,DateTimeFormatter.ofPattern(pattern));
    }
    public static LocalDateTime toLocalDateTime(Date date){
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
    //toDate
    public static Date toDate(String date){
        return toDate(toLocalDateTime(date));
    }
    public static Date toDate(String date,String pattern){
        return toDate(toLocalDateTime(date,pattern));
    }
    public static Date toDate(LocalDate localDate){
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }
    public static Date toDate(LocalDateTime localDateTime){
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }







    public static void main(String[] args) {
        System.out.println(fmt(LocalDate.now()));
    }
}
