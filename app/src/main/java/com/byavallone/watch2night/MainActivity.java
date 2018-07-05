package com.byavallone.watch2night;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //Holds the Adapter instance
    private MoviesViewAdapter mAdapter;
    //TODO Fix the number of columns when in landscape

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Mapping the main Grid
        RecyclerView mainGrid = (RecyclerView) findViewById(R.id.main_gridview);

        //TODO remove this test
        ArrayList<Movies> moviesList = generateFakeData();

        //Setting GridLayout manager for the recyclerView
        mainGrid.setLayoutManager(new GridLayoutManager(MainActivity.this, 3));

        mAdapter = new MoviesViewAdapter(MainActivity.this, moviesList);
        mainGrid.setAdapter(mAdapter);

    }

    //TODO REMOVE This method - TEST ONLY
    private ArrayList<Movies> generateFakeData (){

        ArrayList<Movies> moviesList = new ArrayList<Movies>();
        moviesList.add(new Movies("Thor", "25-12-2012", "https://image.tmdb.org/t/p/w500/rzRwTcFvttcN1ZpX2xv4j3tSdJu.jpg", "8.9", "Filmin muito bom"));
        moviesList.add(new Movies("Thor1", "25-12-2012", "https://image.tmdb.org/t/p/w500/rzRwTcFvttcN1ZpX2xv4j3tSdJu.jpg", "8.9", "Filmin muito bom"));
        moviesList.add(new Movies("Thor2", "25-12-2012", "https://image.tmdb.org/t/p/w500/rzRwTcFvttcN1ZpX2xv4j3tSdJu.jpg", "8.9", "Filmin muito bom"));
        moviesList.add(new Movies("Thor3", "25-12-2012", "https://image.tmdb.org/t/p/w500/rzRwTcFvttcN1ZpX2xv4j3tSdJu.jpg", "8.9", "Filmin muito bom"));
        moviesList.add(new Movies("Thor4", "25-12-2012", "https://image.tmdb.org/t/p/w500/rzRwTcFvttcN1ZpX2xv4j3tSdJu.jpg", "8.9", "Filmin muito bom"));
        moviesList.add(new Movies("Thor5", "25-12-2012", "https://image.tmdb.org/t/p/w500/rzRwTcFvttcN1ZpX2xv4j3tSdJu.jpg", "8.9", "Filmin muito bom"));
        moviesList.add(new Movies("Thor6", "25-12-2012", "https://image.tmdb.org/t/p/w500/rzRwTcFvttcN1ZpX2xv4j3tSdJu.jpg", "8.9", "Filmin muito bom"));
        moviesList.add(new Movies("Thor7", "25-12-2012", "https://image.tmdb.org/t/p/w500/rzRwTcFvttcN1ZpX2xv4j3tSdJu.jpg", "8.9", "Filmin muito bom"));
        moviesList.add(new Movies("Thor8", "25-12-2012", "https://image.tmdb.org/t/p/w500/rzRwTcFvttcN1ZpX2xv4j3tSdJu.jpg", "8.9", "Filmin muito bom"));
        moviesList.add(new Movies("Thor9", "25-12-2012", "https://image.tmdb.org/t/p/w500/rzRwTcFvttcN1ZpX2xv4j3tSdJu.jpg", "8.9", "Filmin muito bom"));
        moviesList.add(new Movies("Thor10", "25-12-2012", "https://image.tmdb.org/t/p/w500/rzRwTcFvttcN1ZpX2xv4j3tSdJu.jpg", "8.9", "Filmin muito bom"));
        moviesList.add(new Movies("Thor11", "25-12-2012", "https://image.tmdb.org/t/p/w500/rzRwTcFvttcN1ZpX2xv4j3tSdJu.jpg", "8.9", "Filmin muito bom"));

        return moviesList;
    }
}
