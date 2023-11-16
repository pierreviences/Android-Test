package com.example.myapplication.data.source;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.example.myapplication.data.model.job.JobResponseItem;
import com.example.myapplication.data.remote.ApiService;

import java.util.List;
import java.util.concurrent.Executor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JobDataSource extends PageKeyedDataSource<Integer, JobResponseItem> {
    private final ApiService apiService;
    private final Executor executor;

    public JobDataSource(ApiService apiService, Executor executor) {
        this.apiService = apiService;
        this.executor = executor;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, JobResponseItem> callback) {
        Call<List<JobResponseItem>> client = apiService.getJob(1); // Halaman pertama
        client.enqueue(new Callback<List<JobResponseItem>>() {
            @Override
            public void onResponse(Call<List<JobResponseItem>> call, Response<List<JobResponseItem>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        List<JobResponseItem> job = response.body();
                        callback.onResult(job, null, 1);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<JobResponseItem>> call, Throwable t) {

            }
        });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, JobResponseItem> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, JobResponseItem> callback) {
        Call<List<JobResponseItem>> client = apiService.getJob(params.key); // Mengambil halaman berikutnya
        client.enqueue(new Callback<List<JobResponseItem>>() {
            @Override
            public void onResponse(Call<List<JobResponseItem>> call, Response<List<JobResponseItem>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        List<JobResponseItem> job = response.body();
                        callback.onResult(job, params.key + 1);
                    }
                }
            }
            @Override
            public void onFailure(Call<List<JobResponseItem>> call, Throwable t) {

            }
        });
    }
}
