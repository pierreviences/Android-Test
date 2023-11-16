package com.example.myapplication.data.source;
import androidx.annotation.NonNull;
import androidx.paging.DataSource;

import com.example.myapplication.data.model.job.JobResponseItem;
import com.example.myapplication.data.remote.ApiService;

import java.util.concurrent.Executor;

public class JobDataSourceFactory extends DataSource.Factory<Integer, JobResponseItem> {
    private final ApiService apiService;
    private final Executor executor;

    public JobDataSourceFactory(ApiService apiService, Executor executor) {
        this.apiService = apiService;
        this.executor = executor;
    }

    @NonNull
    @Override
    public DataSource<Integer, JobResponseItem> create() {
        return new JobDataSource(apiService, executor);
    }
}
