package com.boot.example.hello;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

/**
 * @author lipeng
 * &#064;date 2024/8/5 16:55:16
 */
public class HelloServer {

    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = ServerBuilder.forPort(HelloConst.SERVER_PORT)
                .addService(new HelloServiceImpl())
                .build();
        server.start();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            server.shutdown();
            System.out.println("Successfully stopped the movie controller server");
        }));
        server.awaitTermination();
    }
}
