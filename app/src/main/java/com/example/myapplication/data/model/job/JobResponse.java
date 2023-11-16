package com.example.myapplication.data.model.job;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class JobResponse {

	@SerializedName("Response")
	private List<JobResponseItem> response;

	public List<JobResponseItem> getResponse(){
		return response;
	}
}