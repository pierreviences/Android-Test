package com.example.myapplication.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.example.myapplication.data.model.job.JobResponseItem;
import com.example.myapplication.data.repository.JobRepository;
import com.example.myapplication.utils.Result;

import java.util.List;

public class MainViewModel extends ViewModel {
    private final JobRepository jobRepository;
    public MainViewModel(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }
    public LiveData<Result<List<JobResponseItem>>> getJob() {
        return jobRepository.getJob();
    }
    public LiveData<Result<List<JobResponseItem>>> searchJobByDescription(String description) {
        return jobRepository.searchJobByDescription(description);
    }
}