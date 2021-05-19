package com.example.mymovies.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mymovies.ListActivity;
import com.example.mymovies.Movie;
import com.example.mymovies.MovieDetailsActivity;
import com.example.mymovies.R;
import com.example.mymovies.SearchActivity;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HomeFragment extends Fragment {
    LinearLayout pageLayout;
    private HomeViewModel homeViewModel;
    Movie[] movies;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        pageLayout = (LinearLayout)root.findViewById(R.id.ExploreLinearLayout);


        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

                Movie[] movies = new Movie [3];
                movies[0] = new Movie(616037,"Thor: Love and Thunder", "/ifx23jgqQ8ZjUb9eiGjwbKtf0qQ.jpg", "en", "The fourth installment of the Marvel Studios' Thor movie series.");
                movies[1] = new Movie(507086,"Jurassic World: Dominion", "/4lCQirdaxCHBCeTmwHw7zITx521.jpg", "en", "Plot unknown.");
                movies[2] = new Movie(497698,"Black Widow", "/qAZ0pzat24kLdO3o8ejmbLxyOac.jpg", "en", "Natasha Romanoff, also known as Black Widow, confronts the darker parts of her ledger when a dangerous conspiracy with ties to her past arises. Pursued by a force that will stop at nothing to bring her down, Natasha must deal with her history as a spy and the broken relationships left in her wake long before she became an Avenger.");

                Movie[] movies2 = new Movie [8];
                movies2[0] = new Movie(11,"Star Wars", "/6FfCtAuVAW8XJjZ7eWeLibRLWTw.jpg", "en", "Princess Leia is captured and held hostage by the evil Imperial forces in their effort to take over the galactic Empire. Venturesome Luke Skywalker and dashing captain Han Solo team together with the loveable robot duo R2-D2 and C-3PO to rescue the beautiful princess and restore peace and justice in the Empire.");
                movies2[1] = new Movie(329,"Jurassic Park", "/9i3plLl89DHMz7mahksDaAo7HIS.jpg", "en", "A wealthy entrepreneur secretly creates a theme park featuring living dinosaurs drawn from prehistoric DNA. Before opening day, he invites a team of experts and his two eager grandchildren to experience the park and help calm anxious investors. However, the park is anything but amusing as the security systems go off-line and the dinosaurs escape.");
                movies2[2] = new Movie(105,"Back to the Future", "/xlBivetfrtF84Yx0zISShnNtHYe.jpg", "en", "THis is a description");
                movies2[3] = new Movie(165,"Back to the Future Part II", "/YBawEsTkUZBDajKbd5LiHkmMGf.jpg", "en", "THis is a description");
                movies2[4] = new Movie(196,"Back to the Future Part III", "/crzoVQnMzIrRfHtQw0tLBirNfVg.jpg", "en", "THis is a description");
                movies2[5] = new Movie(49051,"The Hobbit: An Unexpected Journey", "/yHA9Fc37VmpUA5UncTxxo3rTGVA.jpg", "en", "Bilbo Baggins, a hobbit enjoying his quiet life, is swept into an epic quest by Gandalf the Grey and thirteen dwarves who seek to reclaim their mountain home from Smaug, the dragon.");
                movies2[6] = new Movie(1891,"The Empire Strikes Back", "/7BuH8itoSrLExs2YZSsM01Qk2no.jpg", "en", "The epic saga continues as Luke Skywalker, in hopes of defeating the evil Galactic Empire, learns the ways of the Jedi from aging master Yoda. But Darth Vader is more determined than ever to capture Luke. Meanwhile, rebel leader Princess Leia, cocky Han Solo, Chewbacca, and droids C-3PO and R2-D2 are thrown into various stages of capture, betrayal and despair.");
                movies2[7] = new Movie(1771,"Captain America: The First Avenger", "/vSNxAJTlD0r02V9sPYpOjqDZXUK.jpg", "en", "During World War II, Steve Rogers is a sickly man from Brooklyn who's transformed into super-soldier Captain America to aid in the war effort. Rogers must stop the Red Skull – Adolf Hitler's ruthless head of weaponry, and the leader of an organization that intends to use a mysterious device of untold powers for world domination.");

                addListToPage(movies,"Upcoming Movies", 0);

                addListToPage(movies2,"Popular Movies", 1);
            }
        });
        return root;
    }



    //this method will add the
    public void addListToPage(Movie[] movies, String listName, int listID) {
        // create the horizontal Scroll View with a layout on the fly and assign it to the ListsLinearLayout
        HorizontalScrollView scrollView = new HorizontalScrollView(getActivity());
        //Create a listLayout
        LinearLayout listLayout = new LinearLayout(getActivity());

        //create the parms for the list layout
        LinearLayout.LayoutParams listLayoutParms = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        listLayout.setLayoutParams(listLayoutParms);
        scrollView.setLayoutParams(listLayoutParms);

        //Create a text view and add it to the pageLayout.
        TextView tv = new TextView(getActivity());
        LinearLayout.LayoutParams tvLp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        tvLp.setMargins(20,80,20,10);
        tv.setTextSize(20);
        tv.setLayoutParams(tvLp);
        tv.setText(listName);
        pageLayout.addView(tv);


        //for each movie in the list, add them to a new layout
        for(int i=0;i<movies.length;i++)
        {
            Movie movie = movies[i];

            //create blank imageView
            ImageView image = new ImageView(getActivity());

            //Set the layout parameters
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(5, 30, 5, 50);
            image.setLayoutParams(lp);

            //When image is clicked it loads the Details activity or fragment
            image.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    //launch the other activity using the img and url to populate the activity.
                    Intent intent = new Intent(getActivity(), MovieDetailsActivity.class);

                    //create movieInfo array to send to the details activity
                    String[] movieInfo = new String[3];
                    movieInfo[0] = movie.getTitle();
                    movieInfo[1] = movie.getUrl();
                    movieInfo[2] = movie.getDesc();

                    //putExtra only allows one value so I am using an array instead.
                    intent.putExtra("MovieInfo", movieInfo);
                    startActivity( intent);
                }
            });

            //Populate image with the movie poster
            Picasso.get().load(movies[i].getUrl()).placeholder(R.drawable.image_placeholder).into(image);

            // Adds the view to the layout
            listLayout.addView(image);
        }

        //add the scroll view to the page layout
        scrollView.addView(listLayout);
        pageLayout.addView(scrollView);
    }
}