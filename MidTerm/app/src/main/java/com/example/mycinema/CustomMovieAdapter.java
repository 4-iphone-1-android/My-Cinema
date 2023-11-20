package com.example.mycinema;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CustomMovieAdapter extends ArrayAdapter<Movie> {
    private ArrayList<Movie> favoriteMovies;
    private Context context;

    public CustomMovieAdapter(Context context, ArrayList<Movie> movies) {
        super(context, 0, movies);
        favoriteMovies = movies;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Movie movie = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.movie_cell_favorite_list, parent, false);
        }

        Button removeButton = convertView.findViewById(R.id.removeButton);
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Movie selectedMovie = favoriteMovies.get(position);
                favoriteMovies.remove(selectedMovie);
                notifyDataSetChanged();
            }
        });

        ImageView imageView = convertView.findViewById(R.id.movieImage);
        TextView textView = convertView.findViewById(R.id.movieName);

        // Set the movie name
        textView.setText(movie.getName());
        byte[] decodedString = android.util.Base64.decode(movie.getBase64Image(), android.util.Base64.DEFAULT);

        // Load the movie image using Glide
        Glide.with(context)
                .load(decodedString)
                .into(imageView);

        return convertView;
    }

}
