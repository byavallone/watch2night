package com.byavallone.watch2night;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        TextView titleView = (TextView) findViewById(R.id.details_title);
        TextView releaseDateView = (TextView) findViewById(R.id.details_release_date);
        TextView synopsisView = (TextView) findViewById(R.id.details_synopsis);
        ImageView posterView = (ImageView) findViewById(R.id.details_poster);

        String title = "";
        String release_date ="";
        String backgroundUrl ="";
        String synopsis = "";

        //Retrieve all data sent though Intent
        Intent intent = getIntent();

        if(intent.hasExtra("title")){
            title = intent.getStringExtra("title");
        }

        if(intent.hasExtra("release_date")){
            release_date = intent.getStringExtra("release_date");
        }

        if(intent.hasExtra("background_url")){
            backgroundUrl = intent.getStringExtra("background_url");
        }

        if(intent.hasExtra("synopsis")){
            synopsis = intent.getStringExtra("synopsis");
        }

        //TODO get the background image
        Picasso.with(DetailsActivity.this).load(backgroundUrl).into(posterView);
        titleView.setText(title);
        releaseDateView.setText(release_date);
        synopsisView.setText(synopsis);
    }
}
