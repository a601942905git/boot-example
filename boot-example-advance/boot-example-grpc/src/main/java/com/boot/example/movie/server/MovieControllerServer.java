package com.boot.example.movie.server;

import com.boot.example.movie.impl.MovieControllerServiceImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

import static com.boot.example.movie.consts.MovieConst.MOVIE_CONTROLLER_SERVICE_PORT;

/**
 * @author lipeng
 * &#064;date 2024/7/31 10:52:11
 */
public class MovieControllerServer {

    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = ServerBuilder.forPort(MOVIE_CONTROLLER_SERVICE_PORT)
                        .addService(new MovieControllerServiceImpl())
                        .build();
        server.start();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            server.shutdown();
            System.out.println("Successfully stopped the movie controller server");
        }));
        server.awaitTermination();
    }
}
