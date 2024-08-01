package com.boot.example.movie.impl;

import com.proto.moviecontroller.MovieControllerServiceGrpc;
import com.proto.moviecontroller.MovieRequest;
import com.proto.moviecontroller.MovieResponse;
import com.proto.moviestore.MovieStoreRequest;
import com.proto.moviestore.MovieStoreServiceGrpc;
import com.proto.recommender.RecommenderRequest;
import com.proto.recommender.RecommenderResponse;
import com.proto.recommender.RecommenderServiceGrpc;
import com.proto.userpreferences.UserPreferencesRequest;
import com.proto.userpreferences.UserPreferencesResponse;
import com.proto.userpreferences.UserPreferencesServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import static com.boot.example.movie.consts.MovieConst.*;

/**
 * @author lipeng
 * &#064;date 2024/7/31 11:26:59
 */
public class MovieControllerServiceImpl extends MovieControllerServiceGrpc.MovieControllerServiceImplBase {

    @Override
    public void getMovie(MovieRequest request, StreamObserver<MovieResponse> responseObserver) {
        String userId = request.getUserid();
        MovieStoreServiceGrpc.MovieStoreServiceBlockingStub movieStoreClient =
                MovieStoreServiceGrpc.newBlockingStub(getChannel(MOVIE_STORE_SERVICE_PORT));

        UserPreferencesServiceGrpc.UserPreferencesServiceStub userPreferencesClient =
                UserPreferencesServiceGrpc.newStub(getChannel(USER_PREFERENCES_SERVICE_PORT));

        RecommenderServiceGrpc.RecommenderServiceStub recommenderClient =
                RecommenderServiceGrpc.newStub(getChannel(RECOMMENDER_SERVICE_PORT));

        CountDownLatch latch = new CountDownLatch(1);
        StreamObserver<RecommenderRequest> recommenderRequestStreamObserver = recommenderClient.getRecommendedMovie(new StreamObserver<>() {
            @Override
            public void onNext(RecommenderResponse value) {
                responseObserver.onNext(MovieResponse.newBuilder().setMovie(value.getMovie()).build());
                System.out.println("Recommended movie " + value.getMovie());
            }

            @Override
            public void onError(Throwable t) {
                responseObserver.onError(t);
                latch.countDown();
            }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
                latch.countDown();
            }
        });

        StreamObserver<UserPreferencesRequest> userPreferencesRequestStreamObserver = userPreferencesClient.getShortlistedMovies(new StreamObserver<>() {
            @Override
            public void onNext(UserPreferencesResponse value) {
                recommenderRequestStreamObserver.onNext(RecommenderRequest.newBuilder().setUserid(userId).setMovie(value.getMovie()).build());
            }

            @Override
            public void onError(Throwable t) {
            }

            @Override
            public void onCompleted() {
                recommenderRequestStreamObserver.onCompleted();
            }
        });

        movieStoreClient.getMovies(MovieStoreRequest.newBuilder().setGenre(request.getGenre()).build()).forEachRemaining(response -> {
            userPreferencesRequestStreamObserver.onNext(UserPreferencesRequest.newBuilder().setUserid(userId).setMovie(response.getMovie()).build());
        });

        userPreferencesRequestStreamObserver.onCompleted();
        try {
            latch.await(3L, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private ManagedChannel getChannel(int port) {
        return ManagedChannelBuilder.forAddress("localhost", port).usePlaintext().build();
    }
}
