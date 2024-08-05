package com.boot.example.controller;

import com.boot.example.controller.request.HelloRequest;
import com.boot.example.controller.response.HelloResponse;
import com.boot.example.hello.HelloProto;
import com.boot.example.hello.HelloServiceGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lipeng
 * &#064;date 2024/8/5 18:01:43
 */
@RestController
public class HelloController {

    @GrpcClient(value = "helloClient")
    private HelloServiceGrpc.HelloServiceBlockingStub helloServiceBlockingStub;

    @PostMapping("/hello")
    public HelloResponse hello(@RequestBody HelloRequest request) {
        HelloProto.HelloRequest helloRequest = HelloProto.HelloRequest.newBuilder()
                .setName(request.getName())
                .build();
        HelloProto.HelloResponse helloResponse = helloServiceBlockingStub.sayHello(helloRequest);
        HelloResponse response = new HelloResponse();
        response.setNames(helloResponse.getNamesList());
        response.setMsg(helloResponse.getMsg());
        return response;
    }
}
