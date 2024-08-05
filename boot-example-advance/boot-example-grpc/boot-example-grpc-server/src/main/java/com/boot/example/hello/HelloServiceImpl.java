package com.boot.example.hello;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

/**
 * @author lipeng
 * &#064;date 2024/8/5 19:51:26
 */
@GrpcService
public class HelloServiceImpl extends HelloServiceGrpc.HelloServiceImplBase {

    @Override
    public void sayHello(HelloProto.HelloRequest request, StreamObserver<HelloProto.HelloResponse> responseObserver) {
        HelloProto.HelloResponse helloResponse = HelloProto.HelloResponse.newBuilder()
                .addNames("test10001")
                .addNames("test10002")
                .addNames("test10003")
                .addNames("test10004")
                .addNames("test10005")
                .setMsg("hello " + request.getName())
                .build();
        responseObserver.onNext(helloResponse);
        responseObserver.onCompleted();
    }
}
