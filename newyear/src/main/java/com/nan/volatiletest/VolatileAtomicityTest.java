package com.nan.volatiletest;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class VolatileAtomicityTest {
    // 使用 volatile 关键字，但不保证原子性
    private volatile int counter = 0;

    Lock lock = new ReentrantLock();

    public void increment() {
        // 非原子操作：读取 -> 修改 -> 写入
        counter++;
    }

    public int getCounter() {
        return counter;
    }

    public static void main(String[] args) throws InterruptedException {
        VolatileAtomicityTest test = new VolatileAtomicityTest();

        // 创建 10 个线程，每个线程对 counter 进行 1000 次自增
        int threadCount = 10;
        int incrementsPerThread = 1000;
        Thread[] threads = new Thread[threadCount];

        for (int i = 0; i < threadCount; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < incrementsPerThread; j++) {
                    test.increment();
                }
            });
            threads[i].start();
        }

        // 等待所有线程执行完毕
        for (Thread thread : threads) {
            thread.join();
        }

        // 输出最终的 counter 值
        System.out.println("Expected counter value: " + (threadCount * incrementsPerThread));
        System.out.println("Actual counter value: " + test.getCounter());
    }
}
