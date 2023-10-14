package com.example.mycinema;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class FavoriteList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_list);
        // Get the list view
        ListView movieListView = findViewById(R.id.movieListView);

        // Get the list of favorite movies from DetailActivity
        ArrayList<Movie> favoriteMovies = DetailActivity.getFavoriteMovies();
        CustomMovieAdapter adapter = new CustomMovieAdapter(this, favoriteMovies);

        // Set the adapter to the list view
        movieListView.setAdapter(adapter);
    }
}