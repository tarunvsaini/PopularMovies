package com.tarun.saini.popularmovies.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ExtrasModel implements Parcelable
{
    private final static String YOUTUBE_URL="https://www.youtube.com/watch?v=";

    @SerializedName("id")
    private String reviewId;

    @SerializedName("author")
    private String authorName;

    @SerializedName("content")
    private String review;

    @SerializedName("url")
    private String reviewUrl;

    @SerializedName("key")
    private String keyUrl;


    public ExtrasModel(String reviewId, String authorName, String review, String reviewUrl, String keyUrl) {
        this.reviewId = reviewId;
        this.authorName = authorName;
        this.review = review;
        this.reviewUrl = reviewUrl;
        this.keyUrl=keyUrl;
    }

    protected ExtrasModel(Parcel in) {
        reviewId = in.readString();
        authorName = in.readString();
        review = in.readString();
        reviewUrl = in.readString();
        keyUrl = in.readString();
    }

    public static final Creator<ExtrasModel> CREATOR = new Creator<ExtrasModel>() {
        @Override
        public ExtrasModel createFromParcel(Parcel in) {
            return new ExtrasModel(in);
        }

        @Override
        public ExtrasModel[] newArray(int size) {
            return new ExtrasModel[size];
        }
    };

    public String getReviewId() {
        return reviewId;
    }

    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getReviewUrl() {
        return reviewUrl;
    }

    public void setReviewUrl(String reviewUrl) {
        this.reviewUrl = reviewUrl;
    }

    public String getKeyUrl() {
        return YOUTUBE_URL+keyUrl;
    }

    public void setKeyUrl(String keyUrl) {
        this.keyUrl = keyUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(reviewId);
        dest.writeString(authorName);
        dest.writeString(review);
        dest.writeString(reviewUrl);
        dest.writeString(keyUrl);
    }
}
