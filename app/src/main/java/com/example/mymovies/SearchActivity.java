package com.example.mymovies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;

public class SearchActivity extends AppCompatActivity {
    GridView gridView;
    LinearLayout layout;
    EditText searchBar;
    Movie[] movies;
    RelativeLayout loadingCircle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        loadingCircle = findViewById(R.id.loadingPanel);
        loadingCircle.setVisibility(View.INVISIBLE);

        gridView =(GridView) findViewById(R.id.gridview_SearchActivity);

        searchBar = findViewById(R.id.editText_Search_title);
        searchBar.requestFocus();
        //layout = (LinearLayout)findViewById(R.id.LinearLayout_searchListLayout);
        CountDownTimer timer = new CountDownTimer(600,200) {
            @Override
            public void onTick(long millisUntilFinished) {
                //do nothing
            }

            @Override
            public void onFinish() {
                getMovies();
            }
        }.start();

        searchBar.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                //todo show loading icon
                loadingCircle.setVisibility(View.VISIBLE);
                //restart timer
                timer.cancel();
                timer.start();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

        });

    }




    private void getMovies() {
        //Toast.makeText(getApplicationContext(),"Loading Movies", Toast.LENGTH_LONG).show();

        //make sure search bar is not empty
        if(!searchBar.getText().toString().isEmpty()){

            // Instantiate the RequestQueue.
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            String url = "https://api.themoviedb.org/3/search/movie?api_key=4cef9861a58f0609b80973fbdbafeb03&language=en-US&page=1&include_adult=false&query="+searchBar.getText().toString();

            JsonObjectRequest jsonObjRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONArray data = response.getJSONArray("results");
                                String[] titles = new String[data.length()];
                                String title = "";
                                movies = new Movie[data.length()];

                                //populate the movies
                                for (int i = 0; i < data.length(); i++)
                                {
                                    //get the info
                                    int id = data.getJSONObject(i).getInt("id");
                                    String lang = data.getJSONObject(i).getString("original_language");
                                    String image = data.getJSONObject(i).getString("poster_path");
                                    String desc = data.getJSONObject(i).getString("overview");
                                    title = data.getJSONObject(i).getString("title");
                                    titles[i] = title;

                                    //add the info into a new Movie
                                    Movie movie = new Movie(id,title,image,lang,desc);
                                    //add the movie into the Movies array
                                    movies[i] = movie;
                                }

                                //populate the layout
                                populateLayout(movies);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    String stringResponse = "Error getting response" + error;
                    Toast.makeText(SearchActivity.this, stringResponse, Toast.LENGTH_LONG).show();
                }
            });

            // Add the request to the RequestQueue.
            queue.add(jsonObjRequest);
        }
        else {
            //empty string change loading Circle back to invisible
            loadingCircle.setVisibility(View.INVISIBLE);

        }
    }




    private void populateLayout(Movie[] movies){
        //gridView.removeAllViews();
        MovieAdapter movieAdapter = new MovieAdapter(SearchActivity.this, movies);
        gridView.setAdapter(movieAdapter);

        loadingCircle.setVisibility(View.INVISIBLE);




        /*

        layout.removeAllViews();

        for(int i=0;i<movies.length;i++) {
            Movie movie = movies[i];

            //create blank imageView
            ImageView image = new ImageView(getApplicationContext());

            //Set the layout parameters
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(0, 25, 0, 25);
            image.setLayoutParams(lp);

            //When image is clicked it loads the Details activity or fragment
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //launch the other activity using the img and url to populate the activity.
                    Intent intent = new Intent(getApplicationContext(), MovieDetailsActivity.class);

                    //create movieInfo array to send to the details activity
                    String[] movieInfo = new String[3];
                    movieInfo[0] = movie.getTitle();
                    movieInfo[1] = movie.getUrl();
                    movieInfo[2] = movie.getDesc();

                    //putExtra only allows one value so I am using an array instead.
                    intent.putExtra("MovieInfo", movieInfo);
                    startActivity(intent);
                }
            });

            //Populate image with the movie poster
            Picasso.get().load(movies[i].getUrl()).placeholder(R.drawable.image_placeholder).into(image);

            // Adds the view to the layout
            layout.addView(image);



        }
        */
    }
}