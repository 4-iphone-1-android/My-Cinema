package com.example.mycinema;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    Movie selectedShape;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getSelectedShape();
        setValues();
    }

    private void getSelectedShape() {
        Intent previousIntent = getIntent();
        String parsedStringID = previousIntent.getStringExtra("id");
        selectedShape = MainActivity.movieList.get(Integer.valueOf(parsedStringID));
//        selectedShape = (Movie) previousIntent.getSerializableExtra("movie");

    }

    private void setValues() {
        TextView txtView = (TextView) findViewById(R.id.movieName);
        ImageView imgView = (ImageView) findViewById(R.id.movieImage);

        txtView.setText(selectedShape.getName());

        imgView.setImageResource(selectedShape.getImage());
    }
}