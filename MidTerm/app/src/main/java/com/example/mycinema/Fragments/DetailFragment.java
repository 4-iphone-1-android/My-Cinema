package com.example.mycinema.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.example.mycinema.Movie;
import com.example.mycinema.R;

import java.util.ArrayList;


public class DetailFragment extends Fragment {

    Movie selectedShape;
    WebView trailer;
    private boolean isBooked = false;
    private Button btn;
    private WebView wv;

    WebSettings trailerSetting;

    private static ArrayList<Movie> favoriteMovies = new ArrayList<>();
    public DetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getSelectedShape();
        setValues(view);
        setUpFavoriteList(view);

        btn = view.findViewById(R.id.book);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                book(view);
            }
        });
    }
    private void setUpFavoriteList(View view) {
        ImageView addToFavoriteButton = view.findViewById(R.id.addFavoriteButton);

        addToFavoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (favoriteMovies.contains(selectedShape)) {
                    removeFromFavorites();
                    addToFavoriteButton.setImageResource(R.drawable.heart_shape); // Change the icon

                    Toast.makeText(getContext(), "Removed from Favorite List",
                            Toast.LENGTH_SHORT).show();
                } else {
                    addToFavorites();
                    addToFavoriteButton.setImageResource(R.drawable.heart_shape); // Change the icon
                    Toast.makeText(getContext(), "Added to Favorite List",
                            Toast.LENGTH_SHORT).show();
                }
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

    private void removeFromFavorites() {
        favoriteMovies.remove(selectedShape);
    }

    // Static method to get the list of favorite movies
    public static ArrayList<Movie> getFavoriteMovies() {
        return favoriteMovies;
    }


    private void getSelectedShape() {
        Bundle previousIntent = getArguments();
        String parsedStringID = previousIntent.getString("movieID");
        if (parsedStringID != null) {
            try {
                int id = Integer.parseInt(parsedStringID);
                selectedShape = HomeFragment.movieList.get(id);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        } else {
            selectedShape = (Movie) previousIntent.getSerializable("movie");
        }

    }

    private void setValues(View view) {
        TextView txtView = (TextView) view.findViewById(R.id.movieName);
        txtView.setText(selectedShape.getName());

        ImageView imgView = (ImageView) view.findViewById(R.id.movieImage);
        byte[] decodedString = android.util.Base64.decode(selectedShape.getBase64Image(), android.util.Base64.DEFAULT);
        Glide.with(this)
                .load(decodedString)
                .into(imgView);

        TextView description = (TextView) view.findViewById(R.id.movieDescription);
        description.setText(selectedShape.getDescription());

        RatingBar ratingBar = (RatingBar) view.findViewById(R.id.movieRating);
        ratingBar.setRating((float) selectedShape.getRating());

        //WebView trailer = (WebView) findViewById(R.id.movieTrailer);
        //trailer.loadUrl(selectedShape.getTrailerURL());

        trailer = view.findViewById(R.id.movieTrailer);
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

    public void book(View view) {
//        Intent intent = new Intent(DetailActivity.this, Booking.class);
//        intent.putExtra("movie", selectedShape);
//        startActivity(intent);

        Fragment fragment = new Fragment();
        Class fragmentClass = BookingFragment.class;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
            Bundle bundle = new Bundle();
            bundle.putSerializable("movie", selectedShape);
            fragment.setArguments(bundle);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (fragment != null) {
            FragmentManager fragmentManager = getParentFragmentManager();
            fragmentManager.beginTransaction().setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                    .replace(R.id.frameLayout, fragment).commit();
        }
    }
}