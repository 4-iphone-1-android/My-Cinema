package com.example.mycinema;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

public class CustomMovieAdapter extends ArrayAdapter<Movie> {

    public CustomMovieAdapter(Context context, ArrayList<Movie> movies) {
        super(context, 0, movies);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Movie movie = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.movie_cell, parent, false);
        }

        ImageView imageView = convertView.findViewById(R.id.movieImage);
        TextView textView = convertView.findViewById(R.id.movieName);

        // Set the movie name
        textView.setText(movie.getName());

        // Set the movie image
        imageView.setImageResource(movie.getImage());

        return convertView;
    }
}
