package com.example.mymovies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.BlurTransformation;

public class MovieDetailsActivity extends AppCompatActivity {
    ImageView details_image;
    TextView title, overview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        title = findViewById(R.id.details_title);
        overview = findViewById(R.id.details_description);


        // Get the Intent that started this activity and extract the array
        Intent intent = getIntent();
        try {
            String[] info = intent.getStringArrayExtra("MovieInfo");
            title.setText(info[0]);
            overview.setText(info[2]);


            ScrollView scrollLayout = (ScrollView) findViewById(R.id.scroll_details);
            details_image  = findViewById(R.id.details_image);

            CustomLayout mCustomLayout = (CustomLayout)findViewById(R.id.custom_layout);
            Picasso.get().load(info[1]).transform(new BlurTransformation(getApplicationContext(), 50, 2)).into(mCustomLayout);
            Picasso.get().load(info[1]).into(details_image);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}