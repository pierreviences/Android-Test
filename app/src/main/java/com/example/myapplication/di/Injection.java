package com.example.myapplication.di;

import android.content.Context;

import com.example.myapplication.data.remote.ApiConfig;
import com.example.myapplication.data.remote.ApiService;
import com.example.myapplication.data.repository.JobRepository;

public class Injection {
    public static JobRepository provideRepository(Context context) {
        ApiService apiService = ApiConfig.getApiService();
        return JobRepository.getInstance(apiService, context);
    }
}
