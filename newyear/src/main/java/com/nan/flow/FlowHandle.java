package com.nan.flow;

import reactor.core.publisher.Flux;
import reactor.core.publisher.SynchronousSink;

public class FlowHandle {
    public static void main(String[] args) {
        Flux<Integer> numbers = Flux.range(1, 10);

        numbers.handle((number, sink) -> {
            if (number % 2 == 0) {
                sink.next(number); // 只传递偶数
            }
        }).log().subscribe(System.out::println);

    }
}
