package com.boot.example.movie.impl;

import com.proto.common.Movie;
import com.proto.recommender.RecommenderRequest;
import com.proto.recommender.RecommenderResponse;
import com.proto.recommender.RecommenderServiceGrpc;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lipeng
 * &#064;date 2024/7/31 11:20:15
 */
public class RecommenderServiceImpl extends RecommenderServiceGrpc.RecommenderServiceImplBase {

    @Override
    public StreamObserver<RecommenderRequest> getRecommendedMovie(
            StreamObserver<RecommenderResponse> responseObserver) {

        return new StreamObserver<>() {
            final List<Movie> movies = new ArrayList<>();

            @Override
            public void onNext(RecommenderRequest value) {
                movies.add(value.getMovie());
            }

            @Override
            public void onError(Throwable t) {
                responseObserver.onError(Status.INTERNAL
                        .withDescription("Internal server error")
                        .asRuntimeException());
            }

            @Override
            public void onCompleted() {
                if (!movies.isEmpty()) {
                    responseObserver.onNext(RecommenderResponse.newBuilder()
                                    .setMovie(findMovieForRecommendation(movies))
                                    .build());
                    responseObserver.onCompleted();
                } else {
                    responseObserver.onError(Status.NOT_FOUND
                                    .withDescription("Sorry, found no movies to recommend!").asRuntimeException());
                }
            }
        };
    }

    private Movie findMovieForRecommendation(List<Movie> movies) {
        int random = new SecureRandom().nextInt(movies.size());
        return movies.stream().skip(random).findAny().orElse(null);
    }
}
