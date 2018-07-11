package com.byavallone.watch2night.data;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.byavallone.watch2night.MoviesViewAdapter;

import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * Loader class used to get a list of movies from the API
 */
public class MoviesAsyncLoader extends AsyncTaskLoader<List<Movies>> {

    private static final String TAG = MoviesViewAdapter.class.getSimpleName();

    public MoviesAsyncLoader(@NonNull Context context) {
        super(context);
    }

    @Nullable
    @Override
    public List<Movies> loadInBackground() {

        String result = "";

        //creating the URL
        URL url = NetworkUtils.createUrl();

        if(url != null){
            try {
                result = NetworkUtils.makeHttpRequest(url);
                Log.d(TAG, ">>>>>>>HTTP request");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(result != null && !result.isEmpty()) {
            List<Movies> moviesList = NetworkUtils.parseFromMoviesString(result);
            return moviesList;
        }
        return null;
    }
}
