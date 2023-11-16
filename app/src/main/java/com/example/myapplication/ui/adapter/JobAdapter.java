package com.example.myapplication.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.data.model.job.JobResponseItem;
import java.util.Objects;
import androidx.recyclerview.widget.ListAdapter;


public class JobAdapter extends ListAdapter<JobResponseItem, JobAdapter.MyViewHolder> {
    public JobAdapter() {
        super(DIFF_CALLBACK);
    }
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_job, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        JobResponseItem jobItem = getItem(position);
        if (jobItem != null) {
            holder.bind(jobItem);
            holder.itemView.setOnClickListener(v -> {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(jobItem);
                }
            });
        }
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle, tvCompany, tvLocation;
        private  ImageView imgTitle;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvCompany = itemView.findViewById(R.id.tvCompany);
            tvLocation = itemView.findViewById(R.id.tvLocation);
            imgTitle = itemView.findViewById(R.id.imgTitle);
        }

        public void bind(JobResponseItem jobItem) {
            tvTitle.setText(jobItem.getTitle());
            tvCompany.setText(jobItem.getCompany());
            tvLocation.setText(jobItem.getLocation());
            Glide.with(itemView)
                    .load(jobItem.getCompanyLogo())
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(imgTitle);
        }
    }

    private static final DiffUtil.ItemCallback<JobResponseItem> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<JobResponseItem>() {
                @Override
                public boolean areItemsTheSame(@NonNull JobResponseItem oldItem, @NonNull JobResponseItem newItem) {
                    return Objects.equals(oldItem.getId(), newItem.getId());
                }

                @Override
                public boolean areContentsTheSame(@NonNull JobResponseItem oldItem, @NonNull JobResponseItem newItem) {
                    return Objects.equals(oldItem, newItem);
                }
            };

    public interface OnItemClickListener {
        void onItemClick(JobResponseItem jobItem);
    }
}
