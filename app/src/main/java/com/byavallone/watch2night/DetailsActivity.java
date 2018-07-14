package com.byavallone.watch2night;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Class used to diplay all the details of a selected movie
 */
public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        //mapping UI
        TextView titleView = findViewById(R.id.details_title);
        TextView releaseDateView = findViewById(R.id.details_release_date);
        TextView voteAverageView = findViewById(R.id.details_vote_average);
        TextView synopsisView = findViewById(R.id.details_synopsis);
        ImageView posterView = findViewById(R.id.details_poster);

        String title = "";
        String release_date ="";
        String vote_average ="";
        String backgroundUrl ="";
        String synopsis = "";

        //Retrieve all data sent though Intent
        Intent intent = getIntent();

        if(intent.hasExtra(getString(R.string.intent_title))){
            title = intent.getStringExtra(getString(R.string.intent_title));
        }

        if(intent.hasExtra(getString(R.string.intent_release_date))){
            release_date = intent.getStringExtra(getString(R.string.intent_release_date));
        }

        if(intent.hasExtra(getString(R.string.intent_vote_average))){
            vote_average = intent.getStringExtra(getString(R.string.intent_vote_average));
        }

        if(intent.hasExtra(getString(R.string.intent_background_url))){
            backgroundUrl = intent.getStringExtra(getString(R.string.intent_background_url));
        }

        if(intent.hasExtra(getString(R.string.intent_synopsis))){
            synopsis = intent.getStringExtra(getString(R.string.intent_synopsis));
        }

        //Setting all UI components with values restored
        Picasso.with(DetailsActivity.this).load(backgroundUrl).into(posterView);
        titleView.setText(title);
        voteAverageView.setText(vote_average);
        releaseDateView.setText(release_date);
        synopsisView.setText(synopsis);
    }
}
