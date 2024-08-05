package com.boot.example.hello;

import com.proto.hello.HelloRequest;
import com.proto.hello.HelloResponse;
import com.proto.hello.HelloServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.TimeUnit;

/**
 * @author lipeng
 * &#064;date 2024/8/5 17:05:58
 */
public class HelloClient4 {

    public static void main(String[] args) throws InterruptedException {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", HelloConst.SERVER_PORT)
                .usePlaintext()
                .build();
        HelloServiceGrpc.HelloServiceStub helloServiceStub = HelloServiceGrpc.newStub(channel);
        StreamObserver<HelloRequest> helloRequestStreamObserver = helloServiceStub.sayHello4(new StreamObserver<>() {
            @Override
            public void onNext(HelloResponse value) {
                System.out.println("hello server response：" + value.getMsg());
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onCompleted() {
                System.out.println("call rpc sayHello3 done");
            }
        });

        for (int i = 0; i < 10; i++) {
            helloRequestStreamObserver.onNext(HelloRequest.newBuilder().setName("java grpc " + (i + 1)).build());
            TimeUnit.SECONDS.sleep(1);
        }

        helloRequestStreamObserver.onCompleted();
        TimeUnit.SECONDS.sleep(3);
    }
}
