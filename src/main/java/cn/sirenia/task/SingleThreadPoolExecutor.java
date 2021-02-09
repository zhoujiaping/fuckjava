package cn.sirenia.task;

import cn.sirenia.exception.ExceptionUtil;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class SingleThreadPoolExecutor extends ThreadPoolExecutor {
    private int id;
    private AtomicInteger committed = new AtomicInteger(0);
    private AtomicInteger completed = new AtomicInteger(0);

    public SingleThreadPoolExecutor(BlockingQueue<Runnable> workQueue,
                                    ThreadFactory threadFactory,
                                    RejectedExecutionHandler handler) {
        super(1, 1, Long.MAX_VALUE, TimeUnit.DAYS, workQueue,threadFactory,handler);
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public <T> Future<T> submit(Callable<T> task) {
        if (task == null) throw new NullPointerException();
        RunnableFuture<T> ftask = newTaskFor(()->{
            try {
                T v = task.call();
                return v;
            }catch (Exception e){
                throw ExceptionUtil.wrapRuntimeException(e);
            }finally {
                completed.incrementAndGet();
            }
        });
        committed.incrementAndGet();
        execute(ftask);
        return ftask;
    }
}
