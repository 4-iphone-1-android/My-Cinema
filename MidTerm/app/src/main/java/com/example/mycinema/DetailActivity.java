package com.example.mycinema;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    Movie selectedShape;
    WebView trailer;
    WebSettings trailerSetting;

    private static ArrayList<Movie> favoriteMovies = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getSelectedShape();
        setValues();
        setUpFavoriteList();
    }

    private void setUpFavoriteList() {
        ImageView addToFavoriteButton = findViewById(R.id.addFavoriteButton);

        addToFavoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToFavorites();
                Toast.makeText(DetailActivity.this, "Added to Favorite List",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addToFavorites() {
        // Add the selected movie to the static favoriteMovies list
        boolean flag = false;
        for (Movie i:favoriteMovies){
            if (i.getName().equals(selectedShape.getName())){
                flag = true;
            }
        }
        if(!flag){
            favoriteMovies.add(selectedShape);
        }

    }

    // Static method to get the list of favorite movies
    public static ArrayList<Movie> getFavoriteMovies() {
        return favoriteMovies;
    }


    private void getSelectedShape() {
        Intent previousIntent = getIntent();
        String parsedStringID = previousIntent.getStringExtra("id");
        selectedShape = MainActivity.movieList.get(Integer.valueOf(parsedStringID));
        selectedShape = (Movie) previousIntent.getSerializableExtra("movie");

    }

    private void setValues() {
        TextView txtView = (TextView) findViewById(R.id.movieName);
        txtView.setText(selectedShape.getName());

        ImageView imgView = (ImageView) findViewById(R.id.movieImage);
        imgView.setImageResource(selectedShape.getImage());

        TextView description = (TextView) findViewById(R.id.movieDescription);
        description.setText(selectedShape.getDescription());

        RatingBar ratingBar = (RatingBar) findViewById(R.id.movieRating);
        ratingBar.setRating((float) selectedShape.getRating());


        trailer = findViewById(R.id.movieTrailer);
        trailerSetting = trailer.getSettings();
        trailerSetting.setJavaScriptEnabled(true);
        trailerSetting.setMediaPlaybackRequiresUserGesture(false);
        trailer.setWebChromeClient(new WebChromeClient());
        trailer.setWebViewClient(new WebViewClient());
        trailer.getSettings().setPluginState(WebSettings.PluginState.ON);

        trailer.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return false;
            }
        });
        trailer.loadUrl(selectedShape.getTrailerURL());

    }
}