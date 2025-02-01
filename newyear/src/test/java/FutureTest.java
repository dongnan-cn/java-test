import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.function.Consumer;

import org.junit.jupiter.api.Test;

public class FutureTest {
    @Test
    void futureTaskTest() {
        // 创建一个Callable任务
        Callable<Integer> callableTask = new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                // 模拟一个耗时的计算任务
                Thread.sleep(2000); // 休眠2秒
                return 42; // 返回计算结果
            }
        };

        // 使用FutureTask包装Callable任务
        FutureTask<Integer> futureTask = new FutureTask<>(callableTask);

        // 创建一个线程来执行FutureTask
        Thread thread = new Thread(futureTask);
        thread.start();

        // 在主线程中做一些其他工作
        System.out.println("other threads");

        System.out.println("main thread continues...");
    }

    @Test
    void completableFutreTest() throws InterruptedException, ExecutionException {
                // 创建两个异步任务
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "Task 1 completed";
        });

        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "Task 2 completed 奥迪";
        });
        System.out.println("main thread continues...");

        // 等待所有任务完成
        CompletableFuture<Void> allFutures = CompletableFuture.allOf(future1, future2);

        // 获取所有任务的结果
        allFutures.get();  // 阻塞直到所有任务完成

        // 获取每个任务的结果
        String result1 = future1.get();
        String result2 = future2.get();

        System.out.println(result1);
        System.out.println(result2);
    }
    


}
