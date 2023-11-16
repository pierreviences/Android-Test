package com.example.myapplication.data.remote;

import com.example.myapplication.data.model.job.JobResponse;
import retrofit2.Call;
import retrofit2.http.GET;

interface ApiService {
    @GET("positions")
    Call<JobResponse> getJob();
}
