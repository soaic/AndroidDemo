package com.soaic.javalib;

import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;

//import sun.misc.Unsafe;

public class VolatileTest {

    private static boolean isOver = false;

    public static void main(String[] args) {
        //volatileTest();

//        try {
//            unsafeOffsetTest();
//        } catch (NoSuchFieldException | IllegalAccessException e) {
//            e.printStackTrace();
//        }



    }

    public static void volatileTest() {
        Thread thread = new Thread(){
            @Override
            public void run() {
                super.run();
                while (!isOver);
            }
        };
        thread.start();

        try {
            Thread.sleep(500L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        isOver = true;
    }


    public static void atomicTest() {
        AtomicInteger atomicInteger = new AtomicInteger(1);
        atomicInteger.incrementAndGet(); //++i
        atomicInteger.decrementAndGet(); //--i
        atomicInteger.getAndIncrement(); //i++
        atomicInteger.getAndDecrement(); //i--
        atomicInteger.getAndAdd(2); //i+2

        //AtomicStampedReference
    }

    public static void adderTest() {
        LongAdder longAdder = new LongAdder();
        longAdder.increment();
        System.out.println("longAdder:" + longAdder);
        longAdder.add(2);
        System.out.println("longAdder:" + longAdder);
        longAdder.longValue();
    }

//    public static void unsafeOffsetTest() throws NoSuchFieldException, IllegalAccessException {
//        Field field = UnsafeTestClass.class.getDeclaredField("value");
//        Unsafe unsafe = getUnsafe();
//        long valueOffset = unsafe.objectFieldOffset(field);
//        System.out.println("valueOffset=" + valueOffset);
//    }

//    public static Unsafe getUnsafe() throws NoSuchFieldException, IllegalAccessException {
//        Field field = Unsafe.class.getDeclaredField("theUnsafe");
//        field.setAccessible(true);
//        return (Unsafe) field.get(Unsafe.class);
//    }

    public class UnsafeTestClass{
        private long value;
        public UnsafeTestClass(){
            this(99);
        }
        public UnsafeTestClass(long i) {
            this.value = i;
        }
        public long value() {
            return value;
        }
    }
}
