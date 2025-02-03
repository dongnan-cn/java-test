package com.nan.cas;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;



public class AtomicReferenceFieldUpdaterExample {
   // 定义一个包含 volatile 引用类型字段的类
   static class Container {
    volatile String value; // 必须是 volatile 的

    public Container(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

public static void main(String[] args) throws InterruptedException {
    // 创建 AtomicReferenceFieldUpdater 实例
    AtomicReferenceFieldUpdater<Container, String> updater =
            AtomicReferenceFieldUpdater.newUpdater(Container.class, String.class, "value");

    // 创建一个 Container 实例
    Container container = new Container("initial");

    // 创建多个线程并发更新 value 字段
    int threadCount = 10;
    Thread[] threads = new Thread[threadCount];
    for (int i = 0; i < threadCount; i++) {
        threads[i] = new Thread(() -> {
            for (int j = 0; j < 1000; j++) {
                // 使用 CAS 操作更新 value 字段
                while (true) {
                    String currentValue = container.getValue();
                    String newValue = currentValue + "-update";
                    if (updater.compareAndSet(container, currentValue, newValue)) {
                        break; // CAS 成功，退出循环
                    }
                }
            }
        });
        threads[i].start();
    }

    // 等待所有线程执行完毕
    for (Thread thread : threads) {
        thread.join();
    }

    // 输出最终的 value 值
    System.out.println("Final value: " + container.getValue());
}
}
