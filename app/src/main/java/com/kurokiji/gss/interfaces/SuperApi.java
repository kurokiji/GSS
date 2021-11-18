package com.kurokiji.gss.interfaces;

import com.kurokiji.gss.models.LogEntry;
import com.kurokiji.gss.models.State;
import com.kurokiji.gss.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface SuperApi {

    public static final String SERVER_URL = "https://superapi.netlify.com/api/";

    @GET("state")
    Call<String> getNewState();

    @GET("log")
    Call<List<LogEntry>> getNewLogEntry();

    @PUT("state")
    Call<State> putState(@Body State state);

    @POST("register")
    Call<User> registerUser(@Body User user);

    @POST("login")
    Call<User> loginUser(@Body User user);
}
