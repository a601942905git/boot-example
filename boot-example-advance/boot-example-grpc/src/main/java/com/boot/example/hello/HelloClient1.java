package com.boot.example.hello;

import com.proto.hello.HelloRequest;
import com.proto.hello.HelloResponse;
import com.proto.hello.HelloServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

/**
 * @author lipeng
 * &#064;date 2024/8/5 17:05:58
 */
public class HelloClient1 {

    public static void main(String[] args) throws InterruptedException {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", HelloConst.SERVER_PORT)
                .usePlaintext()
                .build();
        HelloServiceGrpc.HelloServiceBlockingStub helloServiceBlockingStub = HelloServiceGrpc.newBlockingStub(channel);
        HelloResponse helloResponse = helloServiceBlockingStub.sayHello1(
                HelloRequest.newBuilder().setName("java grpc").build());
        System.out.println("say hello1 method response: " + helloResponse.getMsg());
    }
}
