package com.example;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

/**
 * Created by user on 16-6-14.
 */


/***
 * 有关synchronized理解
 *
 *
 一、当两个并发线程访问同一个对象object中的这个synchronized(this)同步代码块时，一个时间内只能有一个线程得到执行。另一个线程必须等待当前线程执行完这个代码块以后才能执行该代码块。

 二、然而，当一个线程访问object的一个synchronized(this)同步代码块时，另一个线程仍然可以访问该object中的非synchronized(this)同步代码块。

 三、尤其关键的是，当一个线程访问object的一个synchronized(this)同步代码块时，其他线程对object中所有其它synchronized(this)同步代码块的访问将被阻塞。

 四、第三个例子同样适用其它同步代码块。也就是说，当一个线程访问object的一个synchronized(this)同步代码块时，它就获得了这个object的对象锁。结果，其它线程对该object对象所有同步代码部分的访问都被暂时阻塞。
 */
public class 代码同步 implements Runnable {
    public static void main(String args[]) {
        System.out.println(1);
        List arrayList = new ArrayList<>();
        List linkedList = new LinkedList<>();
        Vector vector = new Vector();
        //
        代码同步 thread = new 代码同步();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                thread.m4t1();
            }
        }, "a");
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                thread.m4t2();
            }
        }, "b");

//        t1.start();
//        t2.start();

        /** volatile*/
        for (int i = 0; i < 1000; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    thread.count();
                }
            }).start();
        }
        System.out.println("运行结果:Counter.count=" + count);

    }

    @Override
    public void run() {
        synchronized (this) {
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() + "  synchronized loop " + i);
            }
        }
    }


    public void m4t1() {
        synchronized (this) {
            int i = 5;
            while (i-- > 0) {
                System.out.println(Thread.currentThread().getName() + " : " + i);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ie) {
                }
            }
        }
    }

    public void m4t2() {
        synchronized (this) {
            int i = 5;
            while (i-- > 0) {
                System.out.println(Thread.currentThread().getName() + " : " + i);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ie) {
                }
            }
        }
    }

    /** 对于volatile修饰的变量，jvm虚拟机只是保证从主内存加载到线程工作内存的值是最新的
     * */
    private static int count = 0;

    private synchronized void count() {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {

            }
            count++;
            System.out.println(count);
    }

}


