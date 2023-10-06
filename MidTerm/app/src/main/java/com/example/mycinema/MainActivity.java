package com.example.mycinema;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static ArrayList<Movie> movieList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SearchView searchView = findViewById(R.id.movieSearchView);
        searchView.clearFocus();
        searchView.setOnQueryTextFocusChangeListener((v, hasFocus) -> openSearchBarActivity());

        setUpData();

        ImageView heartButton = findViewById(R.id.showFavoriteButton);
        heartButton.setOnClickListener(this);
    }

    private void setUpData() {
        if(movieList.isEmpty()){
            //Action
            Movie pic_1 = new Movie("0", "Godzilla: King of the Monsters",
                    R.drawable.picture1, "action");
            movieList.add(pic_1);
            Movie pic_2 = new Movie("1", "Aquaman: Home is calling",
                    R.drawable.picture2, "action");
            movieList.add(pic_2);
            Movie pic_3 = new Movie("2", "Avengers: Endgame",
                    R.drawable.picture3 , "action");
            movieList.add(pic_3);
            Movie pic_4 = new Movie("3", "Avatar: The Way of Water",
                    R.drawable.picture4 , "action");
            movieList.add(pic_4);
            Movie pic_5 = new Movie("4", "Mortal Kombat: The Way of Water",
                    R.drawable.picture5 , "action");
            movieList.add(pic_5);
            //Comedy
            Movie pic_6 = new Movie("5", "Good Boys",
                    R.drawable.picture6 , "comedy");
            movieList.add(pic_6);
            Movie pic_7 = new Movie("6","Shazam!",
                    R.drawable.picture7 , "comedy");
            movieList.add(pic_7);
            Movie pic_8 = new Movie("7","Jumanji: Welcome to Jungle",
                    R.drawable.picture8 , "comedy");
            movieList.add(pic_8);
            Movie pic_9 = new Movie("8","Thor: Ragnarok",
                    R.drawable.picture9 , "comedy");
            movieList.add(pic_9);
            Movie pic_10 = new Movie("9","ted",
                    R.drawable.picture10 , "comedy");
            movieList.add(pic_10);
            //Drama
            Movie pic_11 = new Movie("10","Love at First Sight",
                    R.drawable.picture11 , "drama");
            movieList.add(pic_11);
            Movie pic_12 = new Movie("11","Past Lives",
                    R.drawable.picture12 , "drama");
            movieList.add(pic_12);
            Movie pic_13 = new Movie("12","After Everything",
                    R.drawable.picture13 , "drama");
            movieList.add(pic_13);
            Movie pic_14 = new Movie("13","Titanic",
                    R.drawable.picture14 , "drama");
            movieList.add(pic_14);
            Movie pic_15 = new Movie("14","Twilight",
                    R.drawable.picture15 , "drama");
            movieList.add(pic_15);
            //Horror
            Movie pic_16 = new Movie("15","The Nun ",
                    R.drawable.picture16 , "horror");
            movieList.add(pic_16);
            Movie pic_17 = new Movie("16","Saw",
                    R.drawable.picture17 , "horror");
            movieList.add(pic_17);
            Movie pic_18 = new Movie("17","The Conjuring",
                    R.drawable.picture18 , "horror");
            movieList.add(pic_18);
            Movie pic_19 = new Movie("18","The Meg",
                    R.drawable.picture19 , "horror");
            movieList.add(pic_19);
            Movie pic_20 = new Movie("19","X",
                    R.drawable.picture20 , "horror");
            movieList.add(pic_20);
            //Anime
            Movie pic_21 = new Movie("20","Belle",
                    R.drawable.picture21 , "anime");
            movieList.add(pic_21);
            Movie pic_22 = new Movie("21","Spirited Away",
                    R.drawable.picture22 , "anime");
            movieList.add(pic_22);
            Movie pic_23 = new Movie("22","Grave of the Fireflies",
                    R.drawable.picture23 , "anime");
            movieList.add(pic_23);
            Movie pic_24 = new Movie("23","A Silent Voice: The Movie",
                    R.drawable.picture24 , "anime");
            movieList.add(pic_24);
            Movie pic_25 = new Movie("24","Your Name",
                    R.drawable.picture25 , "anime");
            movieList.add(pic_25);

        }

    }


    private void openSearchBarActivity() {
        Intent intent = new Intent(this, SearchBar.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, FavoriteList.class);
        startActivity(intent);
    }
}
