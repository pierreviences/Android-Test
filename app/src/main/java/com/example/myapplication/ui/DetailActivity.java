package com.example.myapplication.ui;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.data.model.job.JobResponseItem;
import com.example.myapplication.databinding.ActivityDetailBinding;

public class DetailActivity extends AppCompatActivity {
    ActivityDetailBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        binding.topAppBar.setNavigationOnClickListener(v -> onBackPressed());
        JobResponseItem jobItem = (JobResponseItem) getIntent().getSerializableExtra("jobItem");
        if (jobItem != null) {
            binding.tvTitle.setText(jobItem.getTitle());
            binding.tvCompany.setText(jobItem.getCompany());
            binding.tvLocation.setText(jobItem.getLocation());
            binding.tvDesc.setText(Html.fromHtml(jobItem.getDescription(), Html.FROM_HTML_MODE_LEGACY));
            binding.tvType.setText(jobItem.getType().equals("Full Time") ? "Yes" : "No");
            makeTextViewClickable(binding.tvUrl, jobItem.getUrl());
            Glide.with(this).load(jobItem.getCompanyLogo()).placeholder(R.drawable.ic_launcher_background).into(binding.imgTitle);
        }
    }
    private void makeTextViewClickable(TextView textView, final String url) {
        SpannableString spannableString = new SpannableString(textView.getText());
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        };

        spannableString.setSpan(clickableSpan, 0, spannableString.length(), 0);
        textView.setText(spannableString);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }


}