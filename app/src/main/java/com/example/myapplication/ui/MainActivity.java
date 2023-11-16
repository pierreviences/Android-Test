package com.example.myapplication.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.example.myapplication.data.model.job.JobResponseItem;
import com.example.myapplication.databinding.ActivityMainBinding;
import com.example.myapplication.ui.adapter.JobAdapter;
import com.example.myapplication.ui.factory.ViewModelFactory;
import com.example.myapplication.utils.Result;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private MainViewModel mainViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        ViewModelFactory factory = ViewModelFactory.getInstance(this);
        mainViewModel = new ViewModelProvider(this, factory).get(MainViewModel.class);

        JobAdapter jobAdapter = new JobAdapter();

        mainViewModel.getJob().observe(this, result -> {
            if (result != null) {
                if (result instanceof Result.Loading){
                    binding.progressBar.setVisibility(View.VISIBLE);
                } else if (result instanceof Result.Success){
                    binding.progressBar.setVisibility(View.GONE);
                    List<JobResponseItem> jobData = ((Result.Success<List<JobResponseItem>>) result).getData();
                    jobAdapter.submitList(jobData);
                } else if (result instanceof Result.Error){
                    binding.progressBar.setVisibility(View.GONE);
                    Toast.makeText(this, "Terjadi kesalahan " + ((Result.Error<List<JobResponseItem>>) result).getError(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.rvJob.setLayoutManager(new LinearLayoutManager(this));
        binding.rvJob.setHasFixedSize(true);
        binding.rvJob.setAdapter(jobAdapter);
        jobAdapter.setOnItemClickListener(jobItem -> {
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra("jobItem", jobItem);
            startActivity(intent);
        });
    }
}