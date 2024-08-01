package com.boot.example.movie.server;

import com.boot.example.movie.consts.MovieConst;
import com.boot.example.movie.impl.RecommenderServiceImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

/**
 * @author lipeng
 * &#064;date 2024/7/31 10:52:11
 */
public class RecommenderServer {

    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = ServerBuilder.forPort(MovieConst.RECOMMENDER_SERVICE_PORT)
                        .addService(new RecommenderServiceImpl())
                        .build();
        server.start();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            server.shutdown();
            System.out.println("Successfully stopped the user preferences server");
        }));
        server.awaitTermination();
    }
}
