package com.kurokiji.gss;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface SuperApi {

    public static final String SERVER_URL = "https://superapi.netlify.com/api/";

    @GET("state")
    Call<String> getNewState();

    @GET("log")
    Call<List<LogEntry>> getNewLogEntry();

    @FormUrlEncoded
    @PUT("state")
    Call<String> putState(@Field("state") String state);
}
