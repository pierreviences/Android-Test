package com.example.myapplication.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.content.Intent;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.data.model.job.JobResponseItem;
import com.example.myapplication.databinding.ActivityMainBinding;
import com.example.myapplication.ui.adapter.JobAdapter;
import com.example.myapplication.ui.factory.ViewModelFactory;
import com.example.myapplication.utils.Result;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private MainViewModel mainViewModel;
    private JobAdapter jobAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        ViewModelFactory factory = ViewModelFactory.getInstance(this);
        mainViewModel = new ViewModelProvider(this, factory).get(MainViewModel.class);
        jobAdapter = new JobAdapter();
        mainViewModel.getJob().observe(this, result -> {
            binding.progressBar.setVisibility(result instanceof Result.Loading ? View.VISIBLE : View.GONE);
            if (result instanceof Result.Success) {
                List<JobResponseItem> jobData = ((Result.Success<List<JobResponseItem>>) result).getData();
                jobAdapter.submitList(jobData);
            } else if (result instanceof Result.Error) {
                Toast.makeText(this, ((Result.Error<List<JobResponseItem>>) result).getError(), Toast.LENGTH_SHORT).show();
            }
        });

        binding.rvJob.setLayoutManager(new LinearLayoutManager(this));
        binding.rvJob.setHasFixedSize(true);
        binding.rvJob.setAdapter(jobAdapter);
        jobAdapter.setOnItemClickListener(jobItem -> startActivity(new Intent(this, DetailActivity.class).putExtra("jobItem", jobItem)));

        setupSearchView();

        binding.imgButton.setOnClickListener(v -> {
            if(binding.layoutExpand.getVisibility() == v.VISIBLE){
                TransitionManager.beginDelayedTransition(binding.cardView, new AutoTransition());
                binding.layoutExpand.setVisibility(v.GONE);
                binding.imgButton.setImageResource(R.drawable.baseline_keyboard_arrow_down_24);
            }else{
                TransitionManager.beginDelayedTransition(binding.cardView, new AutoTransition());
                binding.layoutExpand.setVisibility(v.VISIBLE);
                binding.imgButton.setImageResource(R.drawable.baseline_keyboard_arrow_up_24);
            }
        });
    }
    private void setupSearchView() {
        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                performSearch(query);
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                performSearch(newText);
                return true;
            }
        });
    }

    private void performSearch(String query) {
        mainViewModel.searchJobByDescription(query).observe(this, result -> {
            binding.progressBar.setVisibility(result instanceof Result.Loading ? View.VISIBLE : View.GONE);
            if (result instanceof Result.Success) {
                List<JobResponseItem> jobData = ((Result.Success<List<JobResponseItem>>) result).getData();
                jobAdapter.submitList(jobData);
                updateNoDataVisibility(jobData.isEmpty());
            } else if (result instanceof Result.Error) {
                Toast.makeText(this, ((Result.Error<List<JobResponseItem>>) result).getError(), Toast.LENGTH_SHORT).show();
                updateNoDataVisibility(true);
            }
        });
    }
    private void updateNoDataVisibility(boolean showNoData) {
        binding.noDataTextView.setVisibility(showNoData ? View.VISIBLE : View.GONE);
    }
}