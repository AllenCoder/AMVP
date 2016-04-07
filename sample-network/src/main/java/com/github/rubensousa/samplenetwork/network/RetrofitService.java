package com.github.rubensousa.samplenetwork.network;


import com.github.rubensousa.samplenetwork.model.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitService {

    String BASE_URL = "https://api.github.com";
    String USERS = "users";
    String USERS_SINCE = "since";

    @GET(USERS)
    Call<ArrayList<User>> getUsers(@Query(USERS_SINCE) int since);
}

