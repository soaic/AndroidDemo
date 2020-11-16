package com.soaic.javalib;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;

public class ExecutorsTest {

    public static void main(String[] arg) {

        //newThread();
        //newThreadRunnable();
        //newThreadFactory();
        //newExecutorCached();
        //newCallable();

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


}