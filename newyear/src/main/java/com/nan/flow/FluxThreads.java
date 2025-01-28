package com.nan.flow;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class FluxThreads {
//     public static void main(String[] args) throws InterruptedException {
//         Flux.range(1, 50000)
//             .subscribeOn(Schedulers.single()) // 源头在 single 线程池
//             .map(i -> {
//                 System.out.println("Map on thread: " + Thread.currentThread().getName());
//                 return i * 2;
//             })
//             .publishOn(Schedulers.boundedElastic()) // 后续操作在 parallel 线程池
//             .subscribe(i -> {
//                 System.out.println("Subscribe on thread: " + Thread.currentThread().getName());
//             });

//         // 等待异步操作完成
//         Thread.sleep(1000);
//     }

    public static void main(String[] args) throws InterruptedException {
        Flux<Integer> f = Flux.range(1, 20)
            .map(i -> {
                System.out.println("Map on thread: " + Thread.currentThread().getName());
                return i * 2;
            });
        //
        new Thread(()->f.subscribe(System.out::println)).start();

        // 等待异步操作完成
        Thread.sleep(1000);
    }
}
