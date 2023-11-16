package com.example.myapplication.data.remote;

import com.example.myapplication.data.model.job.JobResponseItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface ApiService {
    @GET("positions.json")
    Call<List<JobResponseItem>> getJob();
    @GET("positions.json")
    Call<List<JobResponseItem>> getJobByDescription(@Query("description") String description);
}
