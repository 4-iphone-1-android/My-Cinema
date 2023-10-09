package com.example.mycinema;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import androidx.appcompat.widget.SearchView;

import android.widget.ListView;

import java.util.ArrayList;

public class SearchBar extends AppCompatActivity {
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_bar);
        initSearchWidgets();
        setUpList();
        setUpOnclickListener();
    }

    private void initSearchWidgets() {
        SearchView searchView = findViewById(R.id.movieSearchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                ArrayList<Movie> filteredMovie = new ArrayList<Movie>();
                for (Movie movie : MainActivity.movieList) {
                    if (movie.getName().toLowerCase().contains(s.toLowerCase())) {
                        filteredMovie.add(movie);
                    }
                }
                if (s.equals("")) {
                    filteredMovie.clear();
                }
                MovieAdapter adapter = new MovieAdapter(getApplicationContext(), 0,
                        filteredMovie);
                listView.setAdapter(adapter);
                return false;

            }
        });
    }

    public void setUpList() {
        listView = findViewById(R.id.movieListView);
        MovieAdapter adapter = new MovieAdapter(this, 0, MainActivity.movieList);
        listView.setAdapter(adapter);
    }

    private void setUpOnclickListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Movie selectShape = (Movie) (listView.getItemAtPosition(position));
                Intent showDetail = new Intent(getApplicationContext(), DetailActivity.class);
                showDetail.putExtra("id", selectShape.getId());
                startActivity(showDetail);
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(SearchBar.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}