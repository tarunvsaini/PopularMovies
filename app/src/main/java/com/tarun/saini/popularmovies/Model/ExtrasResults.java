package com.tarun.saini.popularmovies.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ExtrasResults implements Parcelable
{
    @SerializedName("id")
    private int movieId;
    @SerializedName("page")
    private int reviewPage;
    @SerializedName("results")
    private ArrayList<ExtrasModel> results;
    @SerializedName("total_pages")
    private int totalPages;
    @SerializedName("total_results")
    private int totalResults;


    protected ExtrasResults(Parcel in) {
        movieId = in.readInt();
        reviewPage = in.readInt();
        totalPages = in.readInt();
        results=in.createTypedArrayList(ExtrasModel.CREATOR);
        totalResults = in.readInt();
    }

    public static final Creator<ExtrasResults> CREATOR = new Creator<ExtrasResults>() {
        @Override
        public ExtrasResults createFromParcel(Parcel in) {
            return new ExtrasResults(in);
        }

        @Override
        public ExtrasResults[] newArray(int size) {
            return new ExtrasResults[size];
        }
    };

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public int getReviewPage() {
        return reviewPage;
    }

    public void setReviewPage(int reviewPage) {
        this.reviewPage = reviewPage;
    }

    public ArrayList<ExtrasModel> getResults() {
        return results;
    }

    public void setResults(ArrayList<ExtrasModel> results) {
        this.results = results;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(movieId);
        dest.writeInt(reviewPage);
        dest.writeInt(totalPages);
        dest.writeList(results);
        dest.writeInt(totalResults);
    }
}
