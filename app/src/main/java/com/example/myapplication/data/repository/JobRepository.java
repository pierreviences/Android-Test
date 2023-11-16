package com.example.myapplication.data.repository;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.example.myapplication.R;
import com.example.myapplication.data.model.job.JobResponseItem;
import com.example.myapplication.data.remote.ApiService;
import com.example.myapplication.data.source.JobDataSourceFactory;
import com.example.myapplication.utils.Result;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JobRepository {
    private final ApiService apiService;
    private final Context context;
    private final MediatorLiveData<Result<List<JobResponseItem>>> result = new MediatorLiveData<>();

    private Executor executor;

    private final PagedList.Config pagedListConfig = new PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(7)
            .build();

    private final LivePagedListBuilder<Integer, JobResponseItem> livePagedListBuilder;

    private JobRepository(ApiService apiService,  Context context) {
        this.apiService = apiService;
        this.context = context;
        this.executor = Executors.newFixedThreadPool(5);

        DataSource.Factory<Integer, JobResponseItem> factory = new JobDataSourceFactory(apiService, executor);
        livePagedListBuilder = new LivePagedListBuilder<>(factory, pagedListConfig);
    }
    public LiveData<PagedList<JobResponseItem>> getPagedJob() {
        return livePagedListBuilder.build();
    }
    public MediatorLiveData<Result<List<JobResponseItem>>> getJob() {
        result.setValue(new Result.Loading<>());
        Call<List<JobResponseItem>> client = apiService.getJob(1);
        client.enqueue(new Callback<List<JobResponseItem>>() {
            @Override
            public void onResponse(@NonNull Call<List<JobResponseItem>> call, @NonNull Response<List<JobResponseItem>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        List<JobResponseItem> job = response.body();
                        result.setValue(new Result.Success<>(job));
                    } else {
                        result.setValue(new Result.Error<>(context.getString(R.string.network_error_message)));
                    }
                } else {
                    result.setValue(new Result.Error<>(context.getString(R.string.network_error_message)));
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<JobResponseItem>> call, @NonNull Throwable t) {
                result.setValue(new Result.Error<>(t.getLocalizedMessage()));
            }
        });
        return result;
    }

    public MediatorLiveData<Result<List<JobResponseItem>>> searchJobByDescription(String description) {
        result.setValue(new Result.Loading<>());
        Call<List<JobResponseItem>> client = apiService.getJobByDescription(description);
        client.enqueue(new Callback<List<JobResponseItem>>() {
            @Override
            public void onResponse(@NonNull Call<List<JobResponseItem>> call, @NonNull Response<List<JobResponseItem>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        List<JobResponseItem> job = response.body();
                        result.setValue(new Result.Success<>(job));
                    } else {
                        result.setValue(new Result.Error<>(context.getString(R.string.network_error_message)));
                    }
                } else {
                    result.setValue(new Result.Error<>(context.getString(R.string.network_error_message)));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<JobResponseItem>> call, @NonNull Throwable t) {
                result.setValue(new Result.Error<>(t.getLocalizedMessage()));
            }
        });
        return result;
    }

    public MediatorLiveData<Result<List<JobResponseItem>>> searchJobApply(String description, boolean fulltime, String location) {
        result.setValue(new Result.Loading<>());
        Call<List<JobResponseItem>> client = apiService.getJobSearchApply(description, fulltime, location);
        client.enqueue(new Callback<List<JobResponseItem>>() {
            @Override
            public void onResponse(@NonNull Call<List<JobResponseItem>> call, @NonNull Response<List<JobResponseItem>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        List<JobResponseItem> job = response.body();
                        result.setValue(new Result.Success<>(job));
                    } else {
                        result.setValue(new Result.Error<>(context.getString(R.string.network_error_message)));
                    }
                } else {
                    result.setValue(new Result.Error<>(context.getString(R.string.network_error_message)));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<JobResponseItem>> call, @NonNull Throwable t) {
                result.setValue(new Result.Error<>(t.getLocalizedMessage()));
            }
        });
        return result;
    }


    private static volatile JobRepository instance;
    public static JobRepository getInstance(ApiService apiService, Context context) {
        if (instance == null) {
            synchronized (JobRepository.class) {
                if (instance == null) {
                    instance = new JobRepository(apiService, context);
                }
            }
        }
        return instance;
    }

}
