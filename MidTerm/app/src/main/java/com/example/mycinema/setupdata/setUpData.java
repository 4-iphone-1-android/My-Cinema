package com.example.mycinema.setupdata;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.example.mycinema.Movie;
import com.google.firebase.Firebase;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class setUpData {
    public static ArrayList<Movie> movieList = new ArrayList<>();
    public static ArrayList<Movie> trendingMovies = new ArrayList<>();
    private FirebaseDatabase database;
    private DatabaseReference moviesRef;

    private Context context;
    private ArrayList<Movie> retrieveData(){

    }

    public setUpData(Context context) {
        this.context = context;
    }
    public void pushMovies(List<Movie> movies) {
        // create a database reference to the "movies" node
        database = FirebaseDatabase.getInstance();
        moviesRef = database.getReference("movies");

        // loop through the list of movies
        for (Movie movie : movies) {
            // create a new child reference with a unique key
            DatabaseReference movieRef = moviesRef.push();

            // convert the image file to a base64 string
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), movie.getImage());
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] imageBytes = baos.toByteArray();
            String imageBase64 = Base64.encodeToString(imageBytes, Base64.DEFAULT);

            // set the image field of the movie object to the base64 string
            movie.setBase64Image(imageBase64);

            // push the movie object to the database
            movieRef.setValue(movie);

            // optionally, add a completion listener
            movieRef.setValue(movie, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError error, DatabaseReference ref) {
                    if (error == null) {
                        Toast.makeText(context, "OKAY", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "FAIL", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }



}
