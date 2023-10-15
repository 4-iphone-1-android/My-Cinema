package com.example.mycinema;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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

        // Set up the sorting spinner
        Spinner sortSpinner = findViewById(R.id.sortSpinner);
        ArrayAdapter<CharSequence> sortAdapter = ArrayAdapter.createFromResource(this,
                R.array.sorting_options, android.R.layout.simple_spinner_item);
        sortAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortSpinner.setAdapter(sortAdapter);

        // Respond to spinner selection
        sortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position == 0) {
                    sortByRatingAscending(favoriteMovies);
                } else if (position == 1) {
                    sortByRatingDescending(favoriteMovies);
                }

                // Notify the adapter that the data has changed
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Do nothing
            }
        });
    }
    private void sortByRatingAscending(ArrayList<Movie> movies) {
        Collections.sort(movies, new Comparator<Movie>() {
            @Override
            public int compare(Movie o1, Movie o2) {
                return Double.compare(o1.getRating(), o2.getRating());
            }
        });
    }

    private void sortByRatingDescending(ArrayList<Movie> movies) {
        Collections.sort(movies, new Comparator<Movie>() {
            @Override
            public int compare(Movie o1, Movie o2) {
                return Double.compare(o2.getRating(), o1.getRating());
            }
        });
    }

}