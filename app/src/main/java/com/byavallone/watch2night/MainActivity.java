package com.byavallone.watch2night;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.Preference;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import com.byavallone.watch2night.data.Movies;
import com.byavallone.watch2night.data.MoviesAsyncLoader;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MoviesViewAdapter.ItemClickListener, LoaderManager.LoaderCallbacks<List<Movies>>, Preference.OnPreferenceChangeListener{

    //Holds the Adapter instance
    private MoviesViewAdapter mAdapter;

    private RecyclerView mGridView;

    private ScrollView mContentView;

    private ProgressBar mLoadingBarView;

    private LinearLayout mWarningView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Mapping the main Grid
        mGridView = (RecyclerView) findViewById(R.id.main_gridview);

        mLoadingBarView = (ProgressBar) findViewById(R.id.loading_bar);

        mContentView = (ScrollView) findViewById(R.id.grid_scroll_view);

        mWarningView = (LinearLayout) findViewById(R.id.warning_layout);

        //Setting GridLayout manager for the recyclerView
        mGridView.setLayoutManager(new GridLayoutManager(MainActivity.this, getResources().getInteger(R.integer.column_number)));

        if(deviceIsConnected(MainActivity.this)) {
            mContentView.setVisibility(View.GONE);
            mLoadingBarView.setVisibility(View.VISIBLE);
            mWarningView.setVisibility(View.GONE);
            getSupportLoaderManager().initLoader(1, null, MainActivity.this).forceLoad();

        }else{
            mWarningView.setVisibility(View.VISIBLE);
            //TODO set the empty view message
        }
    }


    @Override
    public void onItemClick(View view, int position) {
        Movies movie = mAdapter.getItemInPosition(position);
        Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
        intent.putExtra(getString(R.string.intent_title), movie.getTitle());
        intent.putExtra(getString(R.string.intent_release_date), movie.getReleaseDate());
        intent.putExtra(getString(R.string.intent_vote_average), movie.getVoteAverage());
        intent.putExtra(getString(R.string.intent_background_url), movie.getBackgroundUrl());
        intent.putExtra(getString(R.string.intent_synopsis), movie.getSynopsis());
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
        if(movies != null && !movies.isEmpty()){

            mWarningView.setVisibility(View.GONE);
            mContentView.setVisibility(View.VISIBLE);

            if(mAdapter == null){
                mAdapter = new MoviesViewAdapter(MainActivity.this, movies);
                //Setting this activity to the click listener on the adapter
                mAdapter.setClickListener(this);
                mGridView.setAdapter(mAdapter);
            }else{
                mAdapter.setMovies(movies);
            }
        }else{
            mWarningView.setVisibility(View.VISIBLE);
            //TODO empty view
        }

    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Movies>> loader) {
        mAdapter.setMovies(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
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

    @Override
    public boolean onPreferenceChange(Preference preference, Object value) {

        getSupportLoaderManager().initLoader(1, null, MainActivity.this).forceLoad();
        return true;
    }
}
