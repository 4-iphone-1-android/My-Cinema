package com.example.mycinema;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;


public class MovieAdapter extends ArrayAdapter<Movie> {
    public MovieAdapter(@NonNull Context context, int resource, List<Movie> movieList) {
        super(context, resource, movieList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Movie movie = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).
                    inflate(R.layout.movie_cell, parent, false);
        }
        TextView txtView = (TextView) convertView.findViewById(R.id.movieName);
        ImageView imgView = (ImageView) convertView.findViewById(R.id.movieImage);
        txtView.setText(movie.getName());
        imgView.setImageResource(movie.getImage());
        return convertView;
    }

}
