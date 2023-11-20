package com.example.mycinema.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mycinema.Movie;
import com.example.mycinema.R;
import com.google.android.material.datepicker.MaterialDatePicker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class BookingFragment extends Fragment {

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

    public BookingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_booking, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        spinner = view.findViewById(R.id.spinnerTime);
        movieName = view.findViewById(R.id.movieName);
        movieImage = view.findViewById(R.id.movieImage);
        movieTime = view.findViewById(R.id.date);
        quantity = view.findViewById(R.id.quantity);
        btn = view.findViewById(R.id.book);
        wv = view.findViewById(R.id.webView);
        wv.setVisibility(View.GONE);
        quantity.setText("1");
        addList();
        selectedMovie = (Movie) getArguments().getSerializable("movie");
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
                materialDatePicker.show(getParentFragmentManager(), "DATE_PICKER");
                materialDatePicker.addOnPositiveButtonClickListener(selection -> {
                    String format = "dd/MM/yyyy";
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
                    movieTime.setText(simpleDateFormat.format(materialDatePicker.getSelection()));
                });
            }
        });
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                book(view);
//            }
//        });

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

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, selectedTime);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

//    public void book(View view) {
//        if(!isBooked) {
//            Toast.makeText(this, "Booked Successfully", Toast.LENGTH_SHORT).show();
//            movieTime.setText("Time: dd/mm/yyyy");
//            spinner.setSelection(0);
//            quantity.setText("1");
//            wv.setVisibility(View.VISIBLE);
//            wv.loadUrl("https://www.cgv.vn");
//            wv.getSettings().setJavaScriptEnabled(true);
//            isBooked = true;
//            btn.setText("Cancel");
//        }
//        else
//        {
//            btn.setText("Book");
//            wv.loadUrl("about:blank");
//            wv.setVisibility(View.GONE);
//            Toast.makeText(this, "Canceled Successfully", Toast.LENGTH_SHORT).show();
//            isBooked = false;
//        }
//    }
}