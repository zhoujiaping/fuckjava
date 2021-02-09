package cn.sirenia.task;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * 在使用java线程池的时候，我们无法保证两个不同的任务在同一个线程中执行。
 * */
public class ExecutorGroup {
    private int lastSubmitPoolId = 0;
    private final List<SingleThreadPoolExecutor> pools;
    private final LoadBalanceStrategy loadBalanceStrategy;
    private final Random random = new Random();
    public ExecutorGroup(LoadBalanceStrategy strategy,int cpuCoreNum,
                         RejectedExecutionHandler handler,Thread.UncaughtExceptionHandler uncaughtExceptionHandler){
        pools = new ArrayList<>(cpuCoreNum);
        SingleThreadPoolExecutor executor;
        BlockingQueue<Runnable> workQueue;
        ThreadFactory threadFactory;
        for(int i=0;i<cpuCoreNum;i++){
            workQueue = new LinkedBlockingQueue<>();
            threadFactory = new NamedThreadFactory("exec-group-"+i,false,uncaughtExceptionHandler);
            executor = new SingleThreadPoolExecutor(workQueue,threadFactory,handler);
            executor.setId(i);
            pools.add(executor);
        }
        loadBalanceStrategy = strategy;
    }
    public <V> TaskFuture<V> submit(Callable<V> task){
        return submit(loadBalanceStrategy,task);
    }
    private int nextPoolId(){
        if(lastSubmitPoolId>=pools.size()){
            lastSubmitPoolId = 0;
        }else{
            lastSubmitPoolId++;
        }
        return lastSubmitPoolId;
    }
    public <V> TaskFuture<V> submit(LoadBalanceStrategy loadBalanceStrategy,Callable<V> task){
        int idx;
        switch(loadBalanceStrategy){
            case RoundRobin:
                idx = nextPoolId();
                break;
            case LeastActive:
                idx = pools.stream().sorted(Comparator.comparingInt(ThreadPoolExecutor::getActiveCount)).findFirst().map(it->it.getId()).get();
                break;
            case ConsistentHash:
                //not supported,use random
            case Random:
                idx = random.nextInt(pools.size());
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + loadBalanceStrategy);
        }
        return submit(idx,task);
    }
    public <V> TaskFuture<V> submit(int poolId,Callable<V> task){
        lastSubmitPoolId = poolId;
        return new TaskFuture<>(poolId,pools.get(poolId).submit(task));
    }


    public static void main(String[] args) {
        Random random = new Random();
        System.out.println(random.nextInt(10));
        System.out.println(random.nextInt(10));
        System.out.println(random.nextInt(10));
        System.out.println(random.nextInt(10));
        System.out.println(random.nextInt(10));
        System.out.println(random.nextInt(10));
        System.out.println(random.nextInt(10));
        System.out.println(random.nextInt(10));
    }
}
