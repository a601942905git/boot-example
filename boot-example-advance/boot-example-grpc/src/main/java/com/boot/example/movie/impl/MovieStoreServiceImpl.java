package com.boot.example.movie.impl;

import com.proto.common.Genre;
import com.proto.common.Movie;
import com.proto.moviestore.MovieStoreRequest;
import com.proto.moviestore.MovieStoreResponse;
import com.proto.moviestore.MovieStoreServiceGrpc;
import io.grpc.stub.StreamObserver;

import java.util.Arrays;
import java.util.List;

/**
 * @author lipeng
 * &#064;date 2024/7/31 10:53:52
 */
public class MovieStoreServiceImpl extends MovieStoreServiceGrpc.MovieStoreServiceImplBase {

    @Override
    public void getMovies(MovieStoreRequest request, StreamObserver<MovieStoreResponse> responseObserver) {
        List<Movie> movies = Arrays.asList(Movie.newBuilder()
                        .setTitle("No country for old men")
                        .setDescription("Western crime thriller")
                        .setRating(8.1f)
                        .setGenre(Genre.ACTION)
                        .build(),
                Movie.newBuilder()
                        .setTitle("Bourne Ultimatum")
                        .setDescription("Action thriller")
                        .setRating(8.0f)
                        .setGenre(Genre.ACTION)
                        .build(),
                Movie.newBuilder()
                        .setTitle("The taxi driver")
                        .setDescription("Psychological thriller")
                        .setRating(8.2f)
                        .setGenre(Genre.THRILLER)
                        .build(),
                Movie.newBuilder()
                        .setTitle("The Hangover")
                        .setDescription("Hilarious ride")
                        .setRating(7.7f)
                        .setGenre(Genre.COMEDY)
                        .build(),
                Movie.newBuilder()
                        .setTitle("Raiders of the Lost Arc")
                        .setDescription("Expedition in search of the lost arc")
                        .setRating(8.4f)
                        .setGenre(Genre.ACTION).build(),
                Movie.newBuilder()
                        .setTitle("Cast Away")
                        .setDescription("survival story")
                        .setRating(7.8f).setGenre(Genre.DRAMA)
                        .build(),
                Movie.newBuilder()
                        .setTitle("Gladiator")
                        .setDescription("Period drama")
                        .setRating(8.5f)
                        .setGenre(Genre.DRAMA)
                        .build(),
                Movie.newBuilder()
                        .setTitle("Jaws")
                        .setDescription("Shark thrills")
                        .setRating(8.0f)
                        .setGenre(Genre.THRILLER)
                        .build(),
                Movie.newBuilder()
                        .setTitle("Inception")
                        .setDescription("Sci fi action")
                        .setRating(8.8f)
                        .setGenre(Genre.ACTION)
                        .build());
        movies.stream()
                .filter(movie -> movie.getGenre().equals(request.getGenre()))
                .toList()
                .forEach(movie -> responseObserver.onNext(MovieStoreResponse.newBuilder().setMovie(movie).build()));
        responseObserver.onCompleted();
    }
}
