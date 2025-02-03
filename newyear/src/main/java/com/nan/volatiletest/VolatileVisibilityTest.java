package com.nan.volatiletest;

public class VolatileVisibilityTest {
 // 使用 volatile 关键字确保可见性
 private volatile boolean flag = false;

 public void writer() {
     // 模拟一些操作
     try {
         Thread.sleep(1000); // 模拟耗时操作
     } catch (InterruptedException e) {
         e.printStackTrace();
     }
     // 修改 flag 的值
     flag = true;
     System.out.println("Flag set to true by writer thread.");
 }

 public void reader() {
     // 等待 flag 变为 true
     while (!flag) {
         // 空循环，等待 flag 变化
     }
     System.out.println("Flag is true, reader thread detected the change.");
 }

 public static void main(String[] args) {
     VolatileVisibilityTest test = new VolatileVisibilityTest();

     // 创建并启动写线程
     Thread writerThread = new Thread(test::writer);
     writerThread.start();

     // 创建并启动读线程
     Thread readerThread = new Thread(test::reader);
     readerThread.start();

     // 等待两个线程执行完毕
     try {
         writerThread.join();
         readerThread.join();
     } catch (InterruptedException e) {
         e.printStackTrace();
     }

     System.out.println("Test completed.");
 }
}
