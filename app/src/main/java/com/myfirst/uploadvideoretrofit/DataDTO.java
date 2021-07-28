package com.myfirst.uploadvideoretrofit;

import java.util.List;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class DataDTO implements Serializable {

	@SerializedName("id")
	private String id;

	@SerializedName("deletehash")
	private String deletehash;

	@SerializedName("account_id")
	private Object accountId;

	@SerializedName("account_url")
	private Object accountUrl;

	@SerializedName("ad_type")
	private Object adType;

	@SerializedName("ad_url")
	private Object adUrl;

	@SerializedName("title")
	private String title;

	@SerializedName("description")
	private Object description;

	@SerializedName("name")
	private String name;

	@SerializedName("type")
	private String type;

	@SerializedName("width")
	private int width;

	@SerializedName("height")
	private int height;

	@SerializedName("size")
	private int size;

	@SerializedName("views")
	private int views;

	@SerializedName("section")
	private Object section;

	@SerializedName("vote")
	private Object vote;

	@SerializedName("bandwidth")
	private int bandwidth;

	@SerializedName("animated")
	private boolean animated;

	@SerializedName("favorite")
	private boolean favorite;

	@SerializedName("in_gallery")
	private boolean inGallery;

	@SerializedName("in_most_viral")
	private boolean inMostViral;

	@SerializedName("has_sound")
	private boolean hasSound;

	@SerializedName("is_ad")
	private boolean isAd;

	@SerializedName("nsfw")
	private Object nsfw;

	@SerializedName("link")
	private String link;

	@SerializedName("tags")
	private List<Object> tags;

	@SerializedName("processing")
	private ProcessingDTO processing;

	@SerializedName("datetime")
	private int datetime;

	@SerializedName("mp4")
	private String mp4;

	@SerializedName("hls")
	private String hls;
}