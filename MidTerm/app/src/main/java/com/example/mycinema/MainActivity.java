package com.example.mycinema;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import com.example.mycinema.setupdata.setUpData;
import com.google.android.gms.auth.api.signin.internal.Storage;

import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static ArrayList<Movie> movieList = new ArrayList<>();
    public static ArrayList<Movie> trendingMovies = new ArrayList<>();
    private String selectedFilter = "all";
    private DatabaseReference myRef;
    private setUpData mySetupData;
    private FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SearchView searchView = findViewById(R.id.movieSearchView);
        searchView.clearFocus();
        searchView.setOnQueryTextFocusChangeListener((v, hasFocus) -> openSearchBarActivity());

        setUpBrowseCategories();
        setUpTrendingFilm();

        ImageView heartButton = findViewById(R.id.showFavoriteButton);
        heartButton.setOnClickListener(this);

        mySetupData = new setUpData(this);
        mySetupData.pushMovies(movieList);

    }

    private void setUpTrendingFilm(){
        DatabaseReference myRef = database.getReference("trending");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                trendingMovies.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Movie movie = postSnapshot.getValue(Movie.class);
                    trendingMovies.add(movie);
                }
                setUpTrendingFilm();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        RecyclerView recyclerView = findViewById(R.id.trendingList);
        CategoryAdapter adapter = new CategoryAdapter(this, trendingMovies);
        recyclerView.setAdapter(adapter);
    }

    private void setUpData(){
        trendingMovies.add(movieList.get(15));
        trendingMovies.add(movieList.get(16));
        trendingMovies.add(movieList.get(17));
        trendingMovies.add(movieList.get(18));
        trendingMovies.add(movieList.get(19));
    }
    private void setUpBrowseCategories() {
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("movies");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                movieList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Movie movie = postSnapshot.getValue(Movie.class);
                    movieList.add(movie);
                }
                setUpBrowseCategories();
                setUpTrendingFilm();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        RecyclerView recyclerView = findViewById(R.id.categoryList);
        CategoryAdapter adapter = new CategoryAdapter(this, movieList);
        recyclerView.setAdapter(adapter);
    }

    private void filterList(String status){
        selectedFilter = status;

        ArrayList<Movie> filteredMovie = new ArrayList<Movie>();

        for (Movie movie : MainActivity.movieList) {
            if (movie.getGenre().equalsIgnoreCase(status)) {
                filteredMovie.add(movie);
            }
        }
        CategoryAdapter adapter = new CategoryAdapter(this, filteredMovie);
        RecyclerView recyclerView = findViewById(R.id.categoryList);
        recyclerView.setAdapter(adapter);
    }

    public void actionFilterTapped(View view) {
        filterList("action");
    }

    public void comedyFilterTapped(View view) {
        filterList("comedy");
    }

    public void dramaFilterTapped(View view) {
        filterList("drama");
    }

    public void animeFilterTapped(View view) {
        filterList("anime");
    }

    public void horrorFilterTapped(View view) {
        filterList("horror");
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
