package com.byavallone.watch2night;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import com.byavallone.watch2night.data.Movies;
import com.byavallone.watch2night.data.MoviesAsyncLoader;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MoviesViewAdapter.ItemClickListener, LoaderManager.LoaderCallbacks<List<Movies>>{

    //Holds the Adapter instance
    private MoviesViewAdapter mAdapter;

    private RecyclerView mGridView;

    private ScrollView mContentView;

    private ProgressBar mLoadingBarView;
    //TODO Fix the number of columns when in landscape

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Mapping the main Grid
        mGridView = (RecyclerView) findViewById(R.id.main_gridview);

        mLoadingBarView = (ProgressBar) findViewById(R.id.loading_bar);

        mContentView = (ScrollView) findViewById(R.id.grid_scroll_view);

        //Setting GridLayout manager for the recyclerView
        mGridView.setLayoutManager(new GridLayoutManager(MainActivity.this, 3));

        if(deviceIsConnected(MainActivity.this)) {
            mContentView.setVisibility(View.GONE);
            mLoadingBarView.setVisibility(View.VISIBLE);
            //TODO Hide warning layout
            LoaderManager loader = LoaderManager.getInstance(MainActivity.this);
            loader.initLoader(1, null, MainActivity.this).forceLoad();

        }else{
            //TODO Show network issue message
        }

        //TODO show the loader and the empty view


    }


    @Override
    public void onItemClick(View view, int position) {
        Movies movie = mAdapter.getItemInPosition(position);
        Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
        intent.putExtra("title", movie.getTitle());
        intent.putExtra("release_date", movie.getReleaseDate());
        intent.putExtra("background_url", movie.getBackgroundUrl());
        intent.putExtra("synopsis", movie.getSynopsis());
        startActivity(intent);
    }

    @NonNull
    @Override
    public Loader<List<Movies>> onCreateLoader(int i, @Nullable Bundle bundle) {
        return new MoviesAsyncLoader(MainActivity.this);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Movies>> loader, List<Movies> movies) {

        mLoadingBarView.setVisibility(View.GONE);
        //TODO remove the empty view
        if(movies != null && !movies.isEmpty()){
            if(mAdapter == null){
                mContentView.setVisibility(View.VISIBLE);
                mAdapter = new MoviesViewAdapter(MainActivity.this, movies);
                //Setting this activity to the click listener on the adapter
                mAdapter.setClickListener(this);
                mGridView.setAdapter(mAdapter);
            }else{
                mAdapter.setMovies(movies);
            }
        }

    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Movies>> loader) {
        mAdapter.setMovies(null);
    }

    /**
     * MEthod used to check if the device has internet connection
     * @param context
     * @return
     */
    private boolean deviceIsConnected(Context context){
        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }
}
