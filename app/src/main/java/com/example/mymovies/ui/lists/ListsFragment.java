package com.example.mymovies.ui.lists;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.mymovies.ListActivity;
import com.example.mymovies.MainActivity;
import com.example.mymovies.Movie;
import com.example.mymovies.MovieDetailsActivity;
import com.example.mymovies.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ListsFragment extends Fragment {
    LinearLayout pageLayout;
    private ListsViewModel listsViewModel;
    Movie[] movies;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        listsViewModel =
                new ViewModelProvider(this).get(ListsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_lists, container, false);
        //final TextView textView = root.findViewById(R.id.text_lists);
        pageLayout = (LinearLayout)root.findViewById(R.id.ListsLinearLayout);



        listsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

                //TODO replace with movies from api.
                Movie[] movies = new Movie [5];
                movies[0] = new Movie(1,"This awesome Movie", "", "US", "THis is a description");
                movies[1] = new Movie(2,"This awesome Movie", "", "US", "THis is a description");
                movies[2] = new Movie(3,"This awesome Movie", "", "US", "THis is a description");
                movies[3] = new Movie(4,"This awesome Movie", "", "US", "THis is a description");
                movies[4] = new Movie(5,"another cool image", "", "US", "THis is a description");

                addListToPage(movies,"My Movies", 0);

                addListToPage(movies,"Favorites", 1);

                addListToPage(movies,"List 3", 3);

                addListToPage(movies,"My 4th list", 4);

                addListToPage(movies,"Fifth time's a charm", 5);


            }
        });
        return root;
    }


    //this method will add the
    public void addListToPage(Movie[] movies, String listName, int listID) {
        //TODO create the horizontal Scroll View with a layout on the fly and assign it to the ListsLinearLayout
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
        tvLp.setMargins(20,40,20,10);
        tv.setTextSize(20);
        tv.setLayoutParams(tvLp);
        tv.setText(listName);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //launch the other activity using the img and url to populate the activity.
                Intent intent = new Intent(getActivity(), ListActivity.class);

                //create movieInfo array to send to the details activity
                String[] listInfo = new String[3];
                listInfo[0] = listName;

                //putExtra only allows one value so I am using an array instead.
                intent.putExtra("ListInfo", listInfo);
                startActivity( intent );
            }
        });
        pageLayout.addView(tv);


        //for each movie in the list, add them to a new layout
        for(int i=0;i<movies.length;i++)
        {
            Movie movie = movies[i];

            //create blank imageView
            ImageView image = new ImageView(getActivity());

            //Set the layout parameters
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(0, 25, 0, 25);
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