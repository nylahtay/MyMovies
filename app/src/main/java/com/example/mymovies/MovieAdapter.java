package com.example.mymovies;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MovieAdapter extends BaseAdapter {

    private final Context mContext;
    private final Movie[] movies;

    public MovieAdapter (Context context, Movie[] movies)
    {
        this.mContext = context;
        this.movies = movies;
    }

    @Override
    public int getCount() {
        return movies.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 1
        final Movie movie = movies[position];

        // 2
        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.linearlayout_movie, null);
        }

        // 3
        final ImageView imageView = (ImageView)convertView.findViewById(R.id.imageview_cover_art);
        final TextView nameTextView = (TextView)convertView.findViewById(R.id.textview_movie_title);
        final ImageView imageViewFavorite = (ImageView)convertView.findViewById(R.id.imageview_favorite);

        // 4
        Picasso.get().load(movie.getUrl()).placeholder(R.drawable.image_placeholder).into(imageView);
        nameTextView.setText(movie.getTitle());

        imageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //launch the other activity using the img and url to populate the activity.
                Intent intent = new Intent(mContext.getApplicationContext(), MovieDetailsActivity.class);

                //create movieInfo array to send to the details activity
                String[] movieInfo = new String[3];
                movieInfo[0] = movie.getTitle();
                movieInfo[1] = movie.getUrl();
                movieInfo[2] = movie.getDesc();

                //putExtra only allows one value so I am using an array instead.
                intent.putExtra("MovieInfo", movieInfo);
                mContext.startActivity(intent);
            }
        });

        return convertView;
    }
}