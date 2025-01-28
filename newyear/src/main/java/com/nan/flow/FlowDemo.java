package com.nan.flow;

import java.util.concurrent.Flow;
import java.util.concurrent.Flow.Processor;
import java.util.concurrent.Flow.Publisher;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;
import java.util.concurrent.SubmissionPublisher;

public class FlowDemo {

    static class MyProcessor extends SubmissionPublisher<String> implements Processor<String, String> {

        @Override
        public void onSubscribe(Subscription subscription) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'onSubscribe'");
        }

        @Override
        public void onNext(String item) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'onNext'");
        }

        @Override
        public void onError(Throwable throwable) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'onError'");
        }

        @Override
        public void onComplete() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'onComplete'");
        }

    } 

    public static void main(String[] args) throws InterruptedException {
        SubmissionPublisher<String> publisher = new SubmissionPublisher<>();

        // 创建 Subscriber
        Subscriber<String> subscriber = new Subscriber<>() {
            private Subscription subscription;

            @Override
            public void onNext(String item) {
                System.out.println("Received: " + item);
                subscription.request(1); // 继续请求数据
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
            }

            @Override
            public void onComplete() {
                System.out.println("Done");
            }

            @Override
            public void onSubscribe(Subscription subscription) {
                this.subscription = subscription;
                subscription.request(1); // 请求一个数据
            }
        };

        // 订阅
        publisher.subscribe(subscriber);

        // 发布数据
        for (int i = 0; i < 20; i++) {
            publisher.submit("Hello " + i);
            Thread.sleep(5000);
        }
        

        // 关闭发布者
        publisher.close();

        // 等待订阅者处理完成
        Thread.sleep(1000);
    }
}
