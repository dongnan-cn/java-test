import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

public class FluxTest {

    @Test
    void testThread() throws InterruptedException {
        Flux<Integer> f = Flux.range(1, 20)
                .map(i -> {
                    System.out.println("Map on thread: " + Thread.currentThread().getName());
                    return i * 2;
                });
        //
        new Thread(() -> f.subscribe(System.out::println)).start();
        System.out.println("adsf");
        // 等待异步操作完成
        Thread.sleep(1000);
    }

    @Test
    void test() throws InterruptedException {
        Flux<Integer> map = Flux.range(1, 20)
                .map(i -> {
                    System.out.println("Map on thread: " + Thread.currentThread().getName());
                    return i * 2;
                }).publishOn(Schedulers.parallel());
        new Thread(() -> map.subscribe(i -> {
            System.out.println(i + ": Subscribe on thread: " + Thread.currentThread().getName());
        })).start();
        Thread.sleep(1000);
    }

    @Test
    void testPublishOn() throws InterruptedException {
        {
            Flux<Integer> publishOn = Flux.range(1, 3)
                    .map(i -> {
                        System.out.println("Map on thread: " + Thread.currentThread().getName());
                        return i * 2;
                    })
                    .publishOn(Schedulers.parallel()); // 切换到并行线程池
            publishOn.subscribe(i -> {
                System.out.println(i + "Subscribe to thread: " + Thread.currentThread().getName());
            });

            // 等待异步操作完成
            Thread.sleep(1000);
        }
        {
            System.out.println("-----------2222222-------------");
            Flux.range(1, 3)
                    .map(i -> {
                        System.out.println("Map on thread: " + Thread.currentThread().getName());
                        return i * 2;
                    }).publishOn(Schedulers.parallel()).subscribe(i -> {
                        System.out.println(i + "Subscribe on thread: " + Thread.currentThread().getName());
                    });

            // 等待异步操作完成
            Thread.sleep(1000);
        }
        {
            System.out.println("-----------3333333-------------");
            Flux<Integer> publishOn = Flux.range(1, 3)
                    .map(i -> {
                        System.out.println("1st map on thread: " + Thread.currentThread().getName());
                        return i * 2;
                    })
                    .publishOn(Schedulers.parallel()).map(i -> {
                        System.out.println("2nd map on thread: " + Thread.currentThread().getName());
                        return i * 2;
                    }) // 切换到并行线程池
            ; // 切换到并行线程池
            new Thread(() -> publishOn.subscribe(i -> {
                System.out.println(i + "Subscribe to thread: " + Thread.currentThread().getName());
            })).start();
            // new Thread(() -> publishOn.subscribe(i -> {
            // System.out.println(i + "Subscribe to thread: " +
            // Thread.currentThread().getName());
            // })).start();

            // 等待异步操作完成
            Thread.sleep(1000);
        }

    }

    @Test
    void testPublishOn2() throws InterruptedException {
        Flux<Integer> publishOn = Flux.range(1, 3)
                .map(i -> {
                    System.out.println("1st map on thread: " + Thread.currentThread().getName());
                    return i * 2;
                })
                .publishOn(Schedulers.parallel()).map(i -> {
                    System.out.println("2nd map on thread: " + Thread.currentThread().getName());
                    return i * 2;
                }) // 切换到并行线程池
        ; // 切换到并行线程池
        new Thread(() -> publishOn.subscribe(i -> {
            System.out.println(i + "thread 1 Subscribe to thread: " + Thread.currentThread().getName());
        })).start();
        new Thread(() -> publishOn.subscribe(i -> {
            System.out.println(i + "Thread 2 Subscribe to thread: " + Thread.currentThread().getName());
        })).start();

        // 等待异步操作完成
        Thread.sleep(1000);
    }

    @Test
    void testFilterStrings() {
        Flux<String> flux = Flux.just("apple", "banana", "cherry", "date")
                .filter(s -> s.startsWith("b")); // 过滤以 "b" 开头的字符串
        
        StepVerifier.create(flux)
                .expectNext("banana") // 期望的输出
                .verifyComplete(); // 验证流正常完成
    }

}
