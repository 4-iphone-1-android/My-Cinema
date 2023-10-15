package com.example.mycinema;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.datepicker.MaterialDatePicker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class BookActivity extends AppCompatActivity {

    private Movie selectedMovie;
    private TextView movieName;
    private ImageView movieImage;
    private List<String> selectedTime = new ArrayList<>();
    private TextView movieTime;
    private TextView quantity;
    private Spinner spinner;
    private WebView wv;
    private Button btn;
    private boolean isBooked = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        movieName = findViewById(R.id.movieName);
        movieImage = findViewById(R.id.movieImage);
        spinner = findViewById(R.id.spinnerTime);
        movieTime = findViewById(R.id.date);
        quantity = findViewById(R.id.quantity);
        btn = findViewById(R.id.book);
        wv = findViewById(R.id.webView);
        wv.setVisibility(View.GONE);
        quantity.setText("1");
        addList();
        selectedMovie = (Movie) getIntent().getSerializableExtra("movie");
        if (selectedMovie != null) {
            movieName.setText(selectedMovie.getName());
            movieImage.setImageResource(selectedMovie.getImage());
        }

        movieTime.setText("Time: dd/mm/yyyy");

        movieTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
                builder.setTitleText("Chọn ngày");
                MaterialDatePicker materialDatePicker = builder.build();
                materialDatePicker.show(getSupportFragmentManager(), "DATE_PICKER");
                materialDatePicker.addOnPositiveButtonClickListener(selection -> {
                    String format = "dd/MM/yyyy";
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
                    movieTime.setText(simpleDateFormat.format(materialDatePicker.getSelection()));
                });
            }
        });

    }

    private void addList()
    {
        selectedTime.add("10:00AM - 12:00PM");
        selectedTime.add("12:00PM - 2:00PM");
        selectedTime.add("2:00PM - 4:00PM");
        selectedTime.add("4:00PM - 6:00PM");
        selectedTime.add("6:00PM - 8:00PM");
        selectedTime.add("8:00PM - 10:00PM");
        selectedTime.add("10:00PM - 12:00AM");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, selectedTime);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    public void book(View view) {
        if(!isBooked) {
            Toast.makeText(this, "Booked Successfully", Toast.LENGTH_SHORT).show();
            movieTime.setText("Time: dd/mm/yyyy");
            spinner.setSelection(0);
            quantity.setText("1");
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