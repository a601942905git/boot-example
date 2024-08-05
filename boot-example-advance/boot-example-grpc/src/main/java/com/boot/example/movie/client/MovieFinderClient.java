package com.boot.example.movie.client;

import com.proto.common.Genre;
import com.proto.moviecontroller.MovieControllerServiceGrpc;
import com.proto.moviecontroller.MovieRequest;
import com.proto.moviecontroller.MovieResponse;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;

import static com.boot.example.movie.consts.MovieConst.MOVIE_CONTROLLER_SERVICE_PORT;

/**
 * @author lipeng
 * &#064;date 2024/7/31 11:32:36
 */
public class MovieFinderClient {

    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", MOVIE_CONTROLLER_SERVICE_PORT)
                .usePlaintext()
                .build();
        MovieControllerServiceGrpc.MovieControllerServiceBlockingStub movieFinderClient =
                MovieControllerServiceGrpc.newBlockingStub(channel);
        try {
            MovieResponse movieResponse = movieFinderClient
                    .getMovie(MovieRequest.newBuilder()
                            .setGenre(Genre.ACTION)
                            .setUserid("abc")
                            .build());
            System.out.println("Recommended movie " +
                    movieResponse.getMovie());
        } catch (StatusRuntimeException e) {
            System.out.println("Recommended movie not found!");
            e.printStackTrace();
        }
    }
}
