package com.tarun.saini.popularmovies.MovieAdapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.florent37.glidepalette.GlidePalette;
import com.tarun.saini.popularmovies.Model.MovieModel;
import com.tarun.saini.popularmovies.R;

import java.util.ArrayList;

/**
 * Created by Tarun on 24-07-2016.
 *
 */
public class TopRatedMoviesAdapter extends RecyclerView.Adapter<TopRatedMoviesAdapter.TopViewHolder> {

    private ArrayList<MovieModel> mMovies;
    private Context mContext;
    private static final String BASE_POSTER_URL = "http://image.tmdb.org/t/p/w185/";

    private OnTopMovieClickListener mClickListener;


    public interface OnTopMovieClickListener
    {
        void onMovieSelected(int position,ArrayList<MovieModel> movie);
    }

    public TopRatedMoviesAdapter(ArrayList<MovieModel> movies, Context context,OnTopMovieClickListener listener)
    {
        this.mMovies=movies;
        this.mContext=context;
        mClickListener=listener;
    }

    @Override
    public TopRatedMoviesAdapter.TopViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
        return new TopViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(TopRatedMoviesAdapter.TopViewHolder holder, int position)
    {
        MovieModel movieModel =mMovies.get(position);
        String PosterUrl=BASE_POSTER_URL+movieModel.getPoster();
        Glide.with(mContext)
                .load(PosterUrl)
                .listener(GlidePalette.with(PosterUrl)
                        .use(GlidePalette.Profile.MUTED_DARK)
                        .intoBackground(holder.grid_card))
                .into(holder.poster);
        holder.title.setText(movieModel.getTitle());
        holder.description.setText("TMDB Rating: "+String.format("%.1f", movieModel.getVoteAverage())+"/10");

    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    public class TopViewHolder extends RecyclerView.ViewHolder {

        ImageView poster;
        TextView title,description;
        CardView grid_card;
        public TopViewHolder(View itemView) {
            super(itemView);
            poster= (ImageView) itemView.findViewById(R.id.poster);
            title= (TextView) itemView.findViewById(R.id.title);
            description= (TextView) itemView.findViewById(R.id.secondary);
            grid_card= (CardView) itemView.findViewById(R.id.grid_card);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    int position= getAdapterPosition();
                    mClickListener.onMovieSelected(position,mMovies);
                }
            });
        }
    }
}
