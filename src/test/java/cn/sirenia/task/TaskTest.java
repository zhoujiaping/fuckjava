package cn.sirenia.task;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TaskTest {

    /**
     * 这真是太遗憾了，我的笔记本单核的，也没有同步多线程技术，所以这台机器测不了。有机会找个多核的测一下。
     * */
    @Test
    public void testCommit() {
        Random random = new Random();
        ExecutorGroup group = new ExecutorGroup(LoadBalanceStrategy.LeastActive,8, new ThreadPoolExecutor.AbortPolicy(),(r,e)->{
            System.err.println(e.getMessage());
        });
        List<TaskFuture<Integer>> fs = Stream.iterate(100,it->it+1).limit(50)
                .map(it->group.submit(()->{
                    Thread.currentThread().sleep(random.nextInt(100));
                    System.out.println(Thread.currentThread().getName());
                    return it;
                })).collect(Collectors.toList());
        fs.forEach(it->{
            try{
                System.out.println(it.get());
            }catch (Exception e){
                System.err.println(e.getMessage());
            }
        });

    }
    @Test
    public void testLeastActive() {
        Random random = new Random();
        ExecutorGroup group = new ExecutorGroup(LoadBalanceStrategy.LeastActive,8, new ThreadPoolExecutor.AbortPolicy(),(r,e)->{
            System.err.println(e.getMessage());
        });
        List<TaskFuture<Integer>> fs = Stream.iterate(100,it->it+1).limit(50)
                .map(it->group.submit(()->{
                    Thread.currentThread().sleep(random.nextInt(100));
                    System.out.println(Thread.currentThread().getName());
                    return it;
                })).collect(Collectors.toList());
        fs.forEach(it->{
            try{
                System.out.println(it.get());
            }catch (Exception e){
                System.err.println(e.getMessage());
            }
        });

    }
    @Test
    public void testInSameThread() {
        Random random = new Random();
        ExecutorGroup group = new ExecutorGroup(LoadBalanceStrategy.LeastActive,8, new ThreadPoolExecutor.AbortPolicy(),(r,e)->{
            System.err.println(e.getMessage());
        });
        BlockingQueue q = new LinkedBlockingQueue();
        List<TaskFuture<Integer>> fs1 = Stream.iterate(100,it->it+1).limit(50)
                .map(it->group.submit(0,()->{
                    Thread.currentThread().sleep(random.nextInt(100));
                    System.out.println(Thread.currentThread().getName());
                    if(!Thread.currentThread().getName().equals("exec-group-0-thread-1")){
                        System.out.println("wtf?");
                    }
                    q.add(Thread.currentThread().getName());
                    return it;
                })).collect(Collectors.toList());
        List<TaskFuture<Integer>> fs2 = Stream.iterate(200,it->it+1).limit(50)
                .map(it->group.submit(1,()->{
                    Thread.currentThread().sleep(random.nextInt(100));
                    System.out.println(Thread.currentThread().getName());
                    if(!Thread.currentThread().getName().equals("exec-group-1-thread-1")){
                        System.out.println("wtf?");
                    }
                    q.add(Thread.currentThread().getName());
                    return it;
                })).collect(Collectors.toList());
        List<TaskFuture<Integer>> fs = new ArrayList<>();
        fs.addAll(fs1);
        fs.addAll(fs2);
        fs.forEach(it->{
            try{
                System.out.println(it.get());
            }catch (Exception e){
                System.err.println(e.getMessage());
            }
        });
        long count1 = q.stream().filter(it->it.equals("exec-group-0-thread-1")).count();
        long count2 = q.stream().filter(it->it.equals("exec-group-1-thread-1")).count();
        System.out.println("count1="+count1+",count2="+count2);
        //assert count==50L;
    }
}
