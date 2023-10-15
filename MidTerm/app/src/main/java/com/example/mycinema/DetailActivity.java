package com.example.mycinema;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    Movie selectedShape;
    WebView trailer;
    private boolean isBooked = false;
    private Button btn;
    private WebView wv;


    private static ArrayList<Movie> favoriteMovies = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getSelectedShape();
        setValues();
        setUpFavoriteList();
        btn = findViewById(R.id.book);
        wv = findViewById(R.id.webView);
        wv.setVisibility(View.GONE);
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
        favoriteMovies.add(selectedShape);
    }

    // Static method to get the list of favorite movies
    public static ArrayList<Movie> getFavoriteMovies() {
        return favoriteMovies;
    }


    private void getSelectedShape() {
        Intent previousIntent = getIntent();
        String parsedStringID = previousIntent.getStringExtra("id");
        if (parsedStringID != null) {
            try {
                int id = Integer.parseInt(parsedStringID);
                selectedShape = MainActivity.movieList.get(id);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        } else {
            selectedShape = (Movie) previousIntent.getSerializableExtra("movie");
        }

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

        //WebView trailer = (WebView) findViewById(R.id.movieTrailer);
        //trailer.loadUrl(selectedShape.getTrailerURL());

        trailer = (WebView) findViewById(R.id.movieTrailer);

        String videoStr = "<html><body>Promo video<br><iframe width=\"100%\" height=\"315\" " +
                "src=\"https://www.youtube.com/embed/\"" + selectedShape.getTrailerID() +
                "frameborder=\"0\" allowfullscreen></iframe></body></html>";

        trailer.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return false;
            }
        });
        trailer.loadData(videoStr, "text/html", "utf-8");

    }

    public void book(View view) {
        if(!isBooked) {
            Toast.makeText(this, "Booked Successfully", Toast.LENGTH_SHORT).show();
            wv.setVisibility(View.VISIBLE);
            wv.loadUrl("https://www.cgv.vn");
            wv.getSettings().setJavaScriptEnabled(true);
            isBooked = true;
            btn.setText("Cancel");
        }
        else
        {
            btn.setText("Book");
            wv.loadUrl("about:blank");
            wv.setVisibility(View.GONE);
            Toast.makeText(this, "Canceled Successfully", Toast.LENGTH_SHORT).show();
            isBooked = false;
        }
    }
}