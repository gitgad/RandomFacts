package com.gadik.randomfacts;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Gadi
 */
public interface NumbersApi {

    public final static String BASE_URL = "http://numbersapi.com/random/trivia/";

    @GET("?json")
    Call<Fact> getRandomFact();

}
