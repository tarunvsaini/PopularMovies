package com.tarun.saini.popularmovies.MovieAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tarun.saini.popularmovies.Model.ExtrasModel;
import com.tarun.saini.popularmovies.R;

import java.util.ArrayList;
import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    private ArrayList<ExtrasModel> reviews;

    private Context context;

    public ReviewAdapter(ArrayList<ExtrasModel> movies, Context context) {
        this.reviews = movies;
        this.context = context;
    }



    @Override
    public ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_container, parent, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewViewHolder holder, int position)
    {

        holder.author.setText(reviews.get(position).getAuthorName());
        holder.content.setText(reviews.get(position).getReview());

    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }



    public static class ReviewViewHolder extends RecyclerView.ViewHolder
    {
        TextView author,content;
        public ReviewViewHolder(View itemView)
        {
            super(itemView);
            author= (TextView) itemView.findViewById(R.id.author);
            content= (TextView) itemView.findViewById(R.id.content);
        }
    }
}

