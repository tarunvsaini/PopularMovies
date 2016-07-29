package com.tarun.saini.popularmovies.MovieAdapter;

import android.content.Context;
import android.content.Intent;
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
import com.tarun.saini.popularmovies.MoviesMain.MovieDetails;
import com.tarun.saini.popularmovies.R;

import java.util.List;

/**
 * Created by Tarun on 14-07-2016.
 */
public class PopularMovies extends RecyclerView.Adapter<PopularMovies.MovieViewHolder> {

    private List<MovieModel> mMovies;
    private Context mContext;
    View mView;

    public PopularMovies(List<MovieModel> movies, Context context)
    {
        this.mMovies=movies;
        this.mContext=context;
    }

    @Override
    public PopularMovies.MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        mView= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout,parent,false);
        final MovieViewHolder movieViewHolder=new MovieViewHolder(mView);
        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=movieViewHolder.getAdapterPosition();
                Intent intent=new Intent(mContext, MovieDetails.class);
                intent.putExtra(MovieDetails.DETAILS ,mMovies.get(position));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP );
                mContext.startActivity(intent);


            }
        });
        return movieViewHolder;
    }

    @Override
    public void onBindViewHolder(PopularMovies.MovieViewHolder holder, int position) {

        MovieModel movieModel =mMovies.get(position);
        String PosterUrl=movieModel.getPoster();
        Glide.with(mContext)
                .load(PosterUrl)
                .listener(GlidePalette.with(PosterUrl)
                        .use(GlidePalette.Profile.MUTED_DARK)
                        .intoBackground(holder.grid_card))
                .into(holder.poster);

        holder.title.setText(movieModel.getTitle());
        holder.description.setText("Popularity: " + String.format("%.1f", movieModel.getPopularity()));


    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }


    public static class MovieViewHolder extends RecyclerView.ViewHolder  {

        ImageView poster;
        CardView grid_card;
        TextView title,description;
        public MovieViewHolder(View itemView)
        {
            super(itemView);
            poster= (ImageView) itemView.findViewById(R.id.poster);
            title= (TextView) itemView.findViewById(R.id.title);
            description=(TextView) itemView.findViewById(R.id.details);
            grid_card= (CardView) itemView.findViewById(R.id.grid_card);

        }


    }

}
