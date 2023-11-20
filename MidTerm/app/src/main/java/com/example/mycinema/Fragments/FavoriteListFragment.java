package com.example.mycinema.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mycinema.CustomMovieAdapter;
import com.example.mycinema.Movie;
import com.example.mycinema.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class FavoriteListFragment extends Fragment {
    public FavoriteListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_favorite_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Get the list view
        ListView movieListView = view.findViewById(R.id.movieListView);

        // Get the list of favorite movies from DetailActivity
        ArrayList<Movie> favoriteMovies = DetailFragment.getFavoriteMovies();
        CustomMovieAdapter adapter = new CustomMovieAdapter(getContext(), favoriteMovies);

        // Set the adapter to the list view
        movieListView.setAdapter(adapter);

        // Set up the sorting spinner
        Spinner sortSpinner = view.findViewById(R.id.sortSpinner);
        ArrayAdapter<CharSequence> sortAdapter = ArrayAdapter.createFromResource(getContext(),
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