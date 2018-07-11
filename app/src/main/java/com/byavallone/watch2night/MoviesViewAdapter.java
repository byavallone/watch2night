package com.byavallone.watch2night;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.byavallone.watch2night.data.Movies;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Adapter class reponsable for the main grid
 */
public class MoviesViewAdapter extends RecyclerView.Adapter<MoviesViewAdapter.ViewHolder>{

    private LayoutInflater mInflater;
    private List<Movies> mMoviesList;
    private Context mContext;
    private ItemClickListener mClickListener;

    MoviesViewAdapter(Context context, List<Movies> moviesList){
        mContext = context;
        mMoviesList = moviesList;
        mInflater = LayoutInflater.from(context);
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

    /**
     * Method used to get the item in position
     * @param position
     * @return
     */
    public Movies getItemInPosition(int position){
        return mMoviesList.get(position);
    }

    @Override
    public int getItemCount() {
        return mMoviesList.size();
    }

    /**
     * Method used to update the list on the adapter
     * @param moviesList
     */
    public void setMovies(List<Movies> moviesList){
        mMoviesList = moviesList;
    }

    /**
     * View Holder class used to store and recycles the grid item
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView mPosterView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mPosterView = (ImageView) itemView.findViewById(R.id.movie_item_poster);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(mClickListener!= null){
                mClickListener.onItemClick(view, getAdapterPosition());
            }
        }
    }

    void setClickListener(ItemClickListener itemClickListener){
        mClickListener = itemClickListener;
    }

    //Need to implement this method on the parent activity
    public interface ItemClickListener{
        void onItemClick(View view, int position);
    }
}
