package com.example.mymovies;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.BlurTransformation;

public class ListActivity extends AppCompatActivity {
    GridView gridView;
    Movie[] movies;
    String ListTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        gridView =(GridView) findViewById(R.id.gridview_ListActivity);


        // Get the Intent that started this activity and extract the array
        Intent intent = getIntent();
        try {
            String[] info = intent.getStringArrayExtra("ListInfo");
            ListTitle = info[0];
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.setTitle(ListTitle);

        getMovies();
    }

    private void getMovies() {
        //TODO replace with movies from api.
        movies = new Movie [5];
        movies[0] = new Movie(1,"This awesome Movie", "", "US", "THis is a description");
        movies[1] = new Movie(2,"This awesome Movie", "", "US", "THis is a description");
        movies[2] = new Movie(3,"This awesome Movie", "", "US", "THis is a description");
        movies[3] = new Movie(4,"This awesome Movie", "", "US", "THis is a description");
        movies[4] = new Movie(5,"another cool image", "", "US", "THis is a description");

        populateLayout();
    }

    private void populateLayout() {
        //gridView.removeAllViews();
        MovieAdapter movieAdapter = new MovieAdapter(ListActivity.this, movies);
        gridView.setAdapter(movieAdapter);
    }
}