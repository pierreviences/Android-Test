package com.example.myapplication.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;

import com.example.myapplication.data.model.job.JobResponseItem;
import com.example.myapplication.data.repository.JobRepository;
import com.example.myapplication.utils.Result;

import java.util.List;

public class MainViewModel extends ViewModel {
    private final JobRepository jobRepository;
    public MainViewModel(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }
    public LiveData<PagedList<JobResponseItem>> getPagedJob() {
        return jobRepository.getPagedJob();
    }

    public LiveData<Result<List<JobResponseItem>>> searchJobByDescription(String description) {
        return jobRepository.searchJobByDescription(description);
    }

    public LiveData<Result<List<JobResponseItem>>> searchJobByApply(String description, boolean fulltime, String location) {
        return jobRepository.searchJobApply(description, fulltime, location);
    }
}