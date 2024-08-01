package com.boot.example.movie.server;

import com.boot.example.movie.impl.UserPreferencesServiceImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

import static com.boot.example.movie.consts.MovieConst.USER_PREFERENCES_SERVICE_PORT;

/**
 * @author lipeng
 * &#064;date 2024/7/31 10:52:11
 */
public class UserPreferencesServer {

    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = ServerBuilder.forPort(USER_PREFERENCES_SERVICE_PORT)
                        .addService(new UserPreferencesServiceImpl())
                        .build();
        server.start();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            server.shutdown();
            System.out.println("Successfully stopped the user preferences server");
        }));
        server.awaitTermination();
    }
}
