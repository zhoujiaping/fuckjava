package cn.sirenia;

import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadPoolExecutor;

public abstract class $ {
    public static <T> T todo(){
        throw new RuntimeException("not implemented!");
    }
    public static <T> T todo(String msg){
        throw new RuntimeException("not implemented! "+msg);
    }
    //time
    public static String fmt(LocalDate date){
        return todo();
    }
    public static String fmt(LocalDate date,String pattern){
        return todo();
    }
    public static String fmt(LocalDateTime date, String pattern){
        return todo();
    }
    public static String fmt(Date date){
        return todo();
    }
    public static String fmt(Date date, String pattern){
        return todo();
    }
    public static LocalDate toLocalDate(String date){
        return todo();
    }
    public static LocalDate toLocalDate(String date,String pattern){
        return todo();
    }
    public static LocalDate toLocalDate(Date date){
        return todo();
    }
    public static LocalDateTime toLocalDateTime(String date,String pattern){
        return todo();
    }
    public static LocalDateTime toLocalDateTime(String date){
        return todo();
    }
    public static LocalDateTime toLocalDateTime(Date date){
        return todo();
    }
    //text
    public static String fmt(String date,Object... variables){
        return todo();
    }
    //exception
    public static RuntimeException toRuntimeException(Exception e){
        if(e instanceof RuntimeException){
            return (RuntimeException) e;
        }
        return new RuntimeException(e);
    }
    //regex
    //collections
    public static <T> List<T> listOf(T... itmes){
        return todo();
    }
    public static <T> List<T> mutableListOf(T... itmes){
        return todo();
    }
    public static <T> Set<T> setOf(T... itmes){
        return todo();
    }
    public static <T> Set<T> mutableSetOf(T... itmes){
        return todo();
    }
    public static <K,V> Map<K,V> mapOf(K key,V value){
        return todo();
    }
    public static <K,V> Map<K,V> mapOf(K key1,V value1,K key2,V value2){
        return todo();
    }
    public static <K,V> Map<K,V> mapOf(K key1,V value1,K key2,V value2,K key3,V value3){
        return todo();
    }
    public static <K,V> Map<K,V> mapOf(K key1,V value1,K key2,V value2,K key3,V value3,K key4,V value4){
        return todo();
    }
    //todo 最多支持10个键值对
    public static <T> List<T> mutableMapOf(T... itmes){
        return todo();
    }
    //todo 最多支持10个键值对
    //thread
    public static ThreadPoolExecutor pool(){
        return todo();
    }
    //todo 任务管理，支持将多个任务添加到指定的同一个线程中执行，使它们可以在某个线程中以单线程方式执行
    //log
    public static void wrapLogger(){

    }
    //charset
    public static boolean isUtf8(){
        return todo();
    }
    //concurrent
    public static void lateInit(){

    }
    //io
    public static void eachLine(Path path/*,callback*/){

    }
    //big decimal
    //reflect
    //system
    //bean
    public <T> T copy(T obj){
        return todo();
    }
    //lock
    public <T> T withLock(String lockKey/*,callback*/){
        return todo();
    }
}
