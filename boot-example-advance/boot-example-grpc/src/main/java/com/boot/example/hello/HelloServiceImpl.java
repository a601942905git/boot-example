package com.boot.example.hello;

import com.google.common.collect.Lists;
import com.proto.hello.HelloRequest;
import com.proto.hello.HelloResponse;
import com.proto.hello.HelloServiceGrpc;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author lipeng
 * &#064;date 2024/8/5 16:56:39
 */
public class HelloServiceImpl extends HelloServiceGrpc.HelloServiceImplBase {

    @Override
    public void sayHello1(HelloRequest request, StreamObserver<HelloResponse> responseObserver) {
        HelloResponse helloResponse = HelloResponse.newBuilder()
                .setMsg("Hello " + request.getName())
                .build();
        responseObserver.onNext(helloResponse);
        responseObserver.onCompleted();
    }

    @Override
    public void sayHello2(HelloRequest request, StreamObserver<HelloResponse> responseObserver) {
        for (int i = 0; i < 10; i++) {
            HelloResponse helloResponse = HelloResponse.newBuilder()
                    .setMsg("Hello " + request.getName() + " " + (i + 1))
                    .build();
            responseObserver.onNext(helloResponse);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        responseObserver.onCompleted();
    }

    @Override
    public StreamObserver<HelloRequest> sayHello3(StreamObserver<HelloResponse> responseObserver) {
        List<HelloResponse> helloResponseList = Lists.newArrayList();
        return new StreamObserver<>() {
            @Override
            public void onNext(HelloRequest value) {
                HelloResponse helloResponse = HelloResponse.newBuilder()
                        .setMsg("Hello " + value.getName())
                        .build();
                helloResponseList.add(helloResponse);
            }

            @Override
            public void onError(Throwable t) {
                responseObserver.onError(Status.INTERNAL.withDescription("Internal server error").asRuntimeException());
            }

            @Override
            public void onCompleted() {
                responseObserver.onNext(helloResponseList.get(new Random().nextInt(10)));
                responseObserver.onCompleted();
            }
        };
    }

    @Override
    public StreamObserver<HelloRequest> sayHello4(StreamObserver<HelloResponse> responseObserver) {
        return new StreamObserver<>() {
            @Override
            public void onNext(HelloRequest value) {
                HelloResponse helloResponse = HelloResponse.newBuilder()
                        .setMsg("Hello " + value.getName())
                        .build();
                responseObserver.onNext(helloResponse);
            }

            @Override
            public void onError(Throwable t) {
                responseObserver.onError(Status.INTERNAL.withDescription("Internal server error").asRuntimeException());
            }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
            }
        };
    }
}
