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

        if (ListTitle == "My Movies"){
            movies = new Movie [8];
            movies[0] = new Movie(11,"Star Wars", "/6FfCtAuVAW8XJjZ7eWeLibRLWTw.jpg", "en", "Princess Leia is captured and held hostage by the evil Imperial forces in their effort to take over the galactic Empire. Venturesome Luke Skywalker and dashing captain Han Solo team together with the loveable robot duo R2-D2 and C-3PO to rescue the beautiful princess and restore peace and justice in the Empire.");
            movies[1] = new Movie(329,"Jurassic Park", "/9i3plLl89DHMz7mahksDaAo7HIS.jpg", "en", "A wealthy entrepreneur secretly creates a theme park featuring living dinosaurs drawn from prehistoric DNA. Before opening day, he invites a team of experts and his two eager grandchildren to experience the park and help calm anxious investors. However, the park is anything but amusing as the security systems go off-line and the dinosaurs escape.");
            movies[2] = new Movie(105,"Back to the Future", "/xlBivetfrtF84Yx0zISShnNtHYe.jpg", "en", "THis is a description");
            movies[3] = new Movie(165,"Back to the Future Part II", "/YBawEsTkUZBDajKbd5LiHkmMGf.jpg", "en", "THis is a description");
            movies[4] = new Movie(196,"Back to the Future Part III", "/crzoVQnMzIrRfHtQw0tLBirNfVg.jpg", "en", "THis is a description");
            movies[5] = new Movie(49051,"The Hobbit: An Unexpected Journey", "/yHA9Fc37VmpUA5UncTxxo3rTGVA.jpg", "en", "Bilbo Baggins, a hobbit enjoying his quiet life, is swept into an epic quest by Gandalf the Grey and thirteen dwarves who seek to reclaim their mountain home from Smaug, the dragon.");
            movies[6] = new Movie(1891,"The Empire Strikes Back", "/7BuH8itoSrLExs2YZSsM01Qk2no.jpg", "en", "The epic saga continues as Luke Skywalker, in hopes of defeating the evil Galactic Empire, learns the ways of the Jedi from aging master Yoda. But Darth Vader is more determined than ever to capture Luke. Meanwhile, rebel leader Princess Leia, cocky Han Solo, Chewbacca, and droids C-3PO and R2-D2 are thrown into various stages of capture, betrayal and despair.");
            movies[7] = new Movie(1771,"Captain America: The First Avenger", "/vSNxAJTlD0r02V9sPYpOjqDZXUK.jpg", "en", "During World War II, Steve Rogers is a sickly man from Brooklyn who's transformed into super-soldier Captain America to aid in the war effort. Rogers must stop the Red Skull – Adolf Hitler's ruthless head of weaponry, and the leader of an organization that intends to use a mysterious device of untold powers for world domination.");


        }
        else if (ListTitle == "Favorites")
        {
            movies = new Movie [5];
            movies[0] = new Movie(49051,"The Hobbit: An Unexpected Journey", "/yHA9Fc37VmpUA5UncTxxo3rTGVA.jpg", "en", "Bilbo Baggins, a hobbit enjoying his quiet life, is swept into an epic quest by Gandalf the Grey and thirteen dwarves who seek to reclaim their mountain home from Smaug, the dragon.");
            movies[1] = new Movie(1891,"The Empire Strikes Back", "/7BuH8itoSrLExs2YZSsM01Qk2no.jpg", "en", "The epic saga continues as Luke Skywalker, in hopes of defeating the evil Galactic Empire, learns the ways of the Jedi from aging master Yoda. But Darth Vader is more determined than ever to capture Luke. Meanwhile, rebel leader Princess Leia, cocky Han Solo, Chewbacca, and droids C-3PO and R2-D2 are thrown into various stages of capture, betrayal and despair.");
            movies[2] = new Movie(329,"Jurassic Park", "/9i3plLl89DHMz7mahksDaAo7HIS.jpg", "en", "A wealthy entrepreneur secretly creates a theme park featuring living dinosaurs drawn from prehistoric DNA. Before opening day, he invites a team of experts and his two eager grandchildren to experience the park and help calm anxious investors. However, the park is anything but amusing as the security systems go off-line and the dinosaurs escape.");
            movies[3] = new Movie(11,"Star Wars", "/iTQHKziZy9pAAY4hHEDCGPaOvFC.jpg", "en", "Princess Leia is captured and held hostage by the evil Imperial forces in their effort to take over the galactic Empire. Venturesome Luke Skywalker and dashing captain Han Solo team together with the loveable robot duo R2-D2 and C-3PO to rescue the beautiful princess and restore peace and justice in the Empire.");
            movies[4] = new Movie(1771,"Captain America: The First Avenger", "/vSNxAJTlD0r02V9sPYpOjqDZXUK.jpg", "en", "During World War II, Steve Rogers is a sickly man from Brooklyn who's transformed into super-soldier Captain America to aid in the war effort. Rogers must stop the Red Skull – Adolf Hitler's ruthless head of weaponry, and the leader of an organization that intends to use a mysterious device of untold powers for world domination.");

        }
        else {
            movies = new Movie [3];
            movies[0] = new Movie(616037,"Thor: Love and Thunder", "/ifx23jgqQ8ZjUb9eiGjwbKtf0qQ.jpg", "en", "The fourth installment of the Marvel Studios' Thor movie series.");
            movies[1] = new Movie(507086,"Jurassic World: Dominion", "/4lCQirdaxCHBCeTmwHw7zITx521.jpg", "en", "Plot unknown.");
            movies[2] = new Movie(497698,"Black Widow", "/qAZ0pzat24kLdO3o8ejmbLxyOac.jpg", "en", "Natasha Romanoff, also known as Black Widow, confronts the darker parts of her ledger when a dangerous conspiracy with ties to her past arises. Pursued by a force that will stop at nothing to bring her down, Natasha must deal with her history as a spy and the broken relationships left in her wake long before she became an Avenger.");
        }


        populateLayout();
    }

    private void populateLayout() {
        //gridView.removeAllViews();
        MovieAdapter movieAdapter = new MovieAdapter(ListActivity.this, movies);
        gridView.setAdapter(movieAdapter);
    }
}