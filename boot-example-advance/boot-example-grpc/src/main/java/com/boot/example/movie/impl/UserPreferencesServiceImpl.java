package com.boot.example.movie.impl;

import com.proto.common.Movie;
import com.proto.userpreferences.UserPreferencesRequest;
import com.proto.userpreferences.UserPreferencesResponse;
import com.proto.userpreferences.UserPreferencesServiceGrpc;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;

import java.security.SecureRandom;

/**
 * @author lipeng
 * &#064;date 2024/7/31 11:16:58
 */
public class UserPreferencesServiceImpl extends UserPreferencesServiceGrpc.UserPreferencesServiceImplBase {

    @Override
    public StreamObserver<UserPreferencesRequest> getShortlistedMovies(
            StreamObserver<UserPreferencesResponse> responseObserver) {

        return new StreamObserver<>() {
            @Override
            public void onNext(UserPreferencesRequest value) {
                if (isEligible(value.getMovie())) {
                    responseObserver.onNext(UserPreferencesResponse
                                    .newBuilder()
                                    .setMovie(value.getMovie()).build());
                }
            }

            @Override
            public void onError(Throwable t) {
                responseObserver.onError(Status.INTERNAL
                        .withDescription("Internal server error")
                        .asRuntimeException());
            }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
            }
        };
    }
    private boolean isEligible(Movie movie) {
        return (new SecureRandom().nextInt() % 4 != 0);
    }
}
