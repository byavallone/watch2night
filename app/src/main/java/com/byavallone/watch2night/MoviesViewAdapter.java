package com.byavallone.watch2night;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter class reponsable for the main grid
 */
public class MoviesViewAdapter extends RecyclerView.Adapter<MoviesViewAdapter.ViewHolder>{

    //TODO click listener
    private LayoutInflater mInflater;
    private List<Movies> mMoviesList;
    private Context mContext;

    MoviesViewAdapter(Context context, ArrayList<Movies> list){
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mMoviesList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        // inflates the item view and return
        View view = mInflater.inflate(R.layout.movie_grid_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Movies moviesItem = mMoviesList.get(position);
        Picasso.with(mContext).load(moviesItem.getPosterUrl()).into(viewHolder.mPosterView);
    }

    @Override
    public int getItemCount() {
        return mMoviesList.size();
    }

    /**
     * View Holder class used to store and recycles the grid item
     */
    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView mPosterView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mPosterView = (ImageView) itemView.findViewById(R.id.movie_item_poster);

        }
    }
}
