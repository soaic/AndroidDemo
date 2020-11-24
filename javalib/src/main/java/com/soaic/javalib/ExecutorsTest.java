package com.soaic.javalib;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ExecutorsTest {

    private volatile int count;
    private AtomicInteger count1;

    public static void main(String[] arg) {

        //newThread();
        //newThreadRunnable();
        //newThreadFactory();
        //newExecutorCached();
        //newCallable();
        testThreadPool();
    }

    private static void newExecutorCached() {
        ExecutorService executors = Executors.newCachedThreadPool();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("Thread start");
            }
        };
        executors.execute(runnable);
        executors.shutdown();
    }

    private static void newCallable() {
        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "This is Callable";
            }
        };
        ExecutorService executorService = Executors.newCachedThreadPool();
        Future<String> features = executorService.submit(callable);
        try {
            String str = features.get();
            System.out.println("str="+str);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        executorService.shutdown();
    }

    public static void newThread() {
        Thread thread = new Thread(){
            @Override
            public void run() {
                super.run();
                System.out.println("Thread start");
            }
        };
        thread.start();
    }

    public static void newThreadRunnable() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Thread start");
            }
        });
        thread.start();
    }

    public static void newThreadFactory() {
        ThreadFactory threadFactory = new ThreadFactory() {
            int count = 1;
            @Override
            public Thread newThread(Runnable runnable) {
                count++;
                return new Thread(runnable ,"thread:"+ count);
            }
        };
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("Thread start");
            }
        };
        threadFactory.newThread(runnable).start();

    }


    public static void testThreadPool() {
//        SynchronousQueue<Runnable> blockingQueue = new SynchronousQueue<>();
//        LinkedBlockingQueue<Runnable> blockingQueue = new LinkedBlockingQueue<>();
        ArrayBlockingQueue<Runnable> blockingQueue = new ArrayBlockingQueue<>(2);
        ThreadPoolExecutor threadPoolExecutor =
                new ThreadPoolExecutor(3, 4, 60, TimeUnit.SECONDS, blockingQueue, new ThreadFactory() {
                    int count = 1;
                    @Override
                    public Thread newThread(Runnable runnable) {
                        return new Thread(runnable, "thread name " + count++);
                    }
                });

        Runnable run = new Runnable() {
            @Override
            public void run() {
                String threadName = Thread.currentThread().getName();
                System.out.println(threadName+"start!");
                try{
                    Thread.sleep(5000);
                }catch (Exception e){

                }
            }
        };
        threadPoolExecutor.execute(run);
        threadPoolExecutor.execute(run);
        threadPoolExecutor.execute(run);
        threadPoolExecutor.execute(run);
        threadPoolExecutor.execute(run);
        threadPoolExecutor.shutdown();
    }


}