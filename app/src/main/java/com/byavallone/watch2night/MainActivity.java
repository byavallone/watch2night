package com.byavallone.watch2night;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.byavallone.watch2night.data.Movies;
import com.byavallone.watch2night.data.MoviesAsyncLoader;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MoviesViewAdapter.ItemClickListener, LoaderManager.LoaderCallbacks<List<Movies>>, SharedPreferences.OnSharedPreferenceChangeListener {

    //Holds the Adapter instance
    private MoviesViewAdapter mAdapter;

    private RecyclerView mGridView;

    private ScrollView mContentView;

    private ProgressBar mLoadingBarView;

    private LinearLayout mWarningView;

    private TextView mWarningMessageView;

    private ImageView mWarningImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Mapping the main Grid
        mGridView = findViewById(R.id.main_gridview);

        mLoadingBarView = findViewById(R.id.loading_bar);

        mContentView = findViewById(R.id.grid_scroll_view);

        mWarningView = findViewById(R.id.warning_layout);

        mWarningMessageView = findViewById(R.id.warning_message);

        mWarningImageView = findViewById(R.id.warning_icon);

        //Setting GridLayout manager for the recyclerView
        mGridView.setLayoutManager(new GridLayoutManager(MainActivity.this, getResources().getInteger(R.integer.column_number)));

        if(deviceIsConnected(MainActivity.this)) {
            mContentView.setVisibility(View.GONE);
            mLoadingBarView.setVisibility(View.VISIBLE);
            mWarningView.setVisibility(View.GONE);
            getSupportLoaderManager().initLoader(1, null, MainActivity.this).forceLoad();

        }else{
            mWarningView.setVisibility(View.VISIBLE);
            mLoadingBarView.setVisibility(View.GONE);
            mWarningImageView.setImageResource(android.R.drawable.stat_notify_error);
            mWarningMessageView.setText(R.string.warning_no_internet);
        }
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        sharedPrefs.registerOnSharedPreferenceChangeListener(MainActivity.this);
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

        //Hide the loading
        mLoadingBarView.setVisibility(View.GONE);
        //checking if we have a list of movies and if the list is not empty
        if(movies != null && !movies.isEmpty()){
            //Hide the warning UI and show the content UI
            mWarningView.setVisibility(View.GONE);
            mContentView.setVisibility(View.VISIBLE);

            if(mAdapter == null){
                mAdapter = new MoviesViewAdapter(MainActivity.this, movies);
                //Setting this activity to the click listener on the adapter
                mAdapter.setClickListener(this);
                mGridView.setAdapter(mAdapter);
            }else{

                mAdapter.setMovies(movies);
                mAdapter.notifyDataSetChanged();
            }
        }else{
            //In case the list is null or empty we show a no suggestion message
            mWarningView.setVisibility(View.VISIBLE);
            mWarningImageView.setImageResource(android.R.drawable.presence_video_online);
            mWarningMessageView.setText(R.string.warning_no_suggestion);
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
     * Method used to check if the device has internet connection
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
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        if(key.equalsIgnoreCase(getString(R.string.settings_sort_key))){
            getSupportLoaderManager().initLoader(1, null, MainActivity.this).forceLoad();
        }
    }
}
