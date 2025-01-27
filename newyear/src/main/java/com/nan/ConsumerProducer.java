package com.nan;

public class ConsumerProducer {

    public static void main(String[] args) {
        Clerk c = new Clerk();
        Producer producer = new Producer(c);
        Consumer consumer1 = new Consumer(c);
        Consumer consumer2 = new Consumer(c);
        producer.setName("大厂");
        consumer1.setName("用户1");
        consumer2.setName("用户2");
        producer.start();
        consumer1.start();
        consumer2.start();
    }

}

class Producer extends Thread {
    Clerk c;

    Producer(Clerk clerk) {
        this.c = clerk;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            c.increase();
        }

    }

}

class Consumer extends Thread {
    Clerk c;

    Consumer(Clerk clerk) {
        this.c = clerk;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            c.decrease();
        }

    }

    public void setC(Clerk c) {
        this.c = c;
    }

}

class Clerk {
    private final static int MAX = 20;
    private int count = 0;

    public synchronized void increase(){
  
        try {
            if (count >= MAX) {
                this.wait();
            } else {
                count++;
                System.out.println(Thread.currentThread().getName() + " now produced to " + count);
                this.notifyAll();
            }
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        }
    
    }

    public synchronized void decrease(){
        try {
            if (count <= 0) {
                this.wait();
            } else {
                count--;
                System.out.println(Thread.currentThread().getName() + " now decreased to " + count);
                this.notifyAll();
            }
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        }
    }

}