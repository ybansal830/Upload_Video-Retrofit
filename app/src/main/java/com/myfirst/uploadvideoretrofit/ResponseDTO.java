package com.myfirst.uploadvideoretrofit;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class ResponseDTO implements Serializable {

	@SerializedName("status")
	private int status;

	@SerializedName("success")
	private boolean success;

	@SerializedName("data")
	private DataDTO data;
}