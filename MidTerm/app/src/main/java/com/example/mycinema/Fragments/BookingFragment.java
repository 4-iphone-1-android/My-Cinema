package com.example.mycinema.Fragments;

import static com.example.mycinema.LoginActivity.uID;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

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

import com.bumptech.glide.Glide;
import com.example.mycinema.BookActivity;
import com.example.mycinema.Movie;
import com.example.mycinema.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class BookingFragment extends Fragment {

    private Movie selectedMovie;
    private TextView movieName;
    private ImageView movieImage;
    private List<String> selectedTime = new ArrayList<>();
    private TextView movieTime;
    private TextView quantity;
    private Spinner spinner;
    private WebView wv;
    private Button btn,chooseSeats;
    private boolean isBooked = false;

    private BookActivity booking;
    public static List<String> selectedSeats = new ArrayList<>();

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("Booking");
    public static String date = "dd/MM/yyyy";
    public BookingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_booking, container, false);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        spinner = view.findViewById(R.id.spinnerTime);
        movieName = view.findViewById(R.id.movieName);
        movieImage = view.findViewById(R.id.movieImage);
        movieTime = view.findViewById(R.id.date);
        quantity = view.findViewById(R.id.quantity);
        chooseSeats = view.findViewById(R.id.chooseSeats);
        btn = view.findViewById(R.id.book);
        wv = view.findViewById(R.id.webView);
        wv.setVisibility(View.GONE);
        booking = new BookActivity();
        quantity.setText(selectedSeats.size() + "");
        addList();
        selectedMovie = (Movie) getArguments().getSerializable("movie");
        if (selectedMovie != null) {
            byte[] decodedString = android.util.Base64.decode(selectedMovie.getBase64Image(), android.util.Base64.DEFAULT);

            Glide.with(getContext())
                    .load(decodedString)
                    .into(movieImage);
            movieName.setText(selectedMovie.getName());
        }

        movieTime.setText(date);

        movieTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
                builder.setTitleText("Choose date");
                MaterialDatePicker materialDatePicker = builder.build();
                materialDatePicker.show(getParentFragmentManager(), "DATE_PICKER");
                materialDatePicker.addOnPositiveButtonClickListener(selection -> {
                    String format = "dd/MM/yyyy";
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
                    movieTime.setText(simpleDateFormat.format(materialDatePicker.getSelection()));
                    date = simpleDateFormat.format(materialDatePicker.getSelection());
                });
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                book(view);
            }
        });
        if (selectedSeats.size() != 0) {
            chooseSeats.setText("Choose seats (" + selectedSeats.size() + selectedSeats.toString());
        } else {
            chooseSeats.setText("Choose seats");
        }
        chooseSeats.setOnClickListener( v -> {
            if (movieTime.getText().toString().equals("Time: dd/mm/yyyy")) {
                Toast.makeText(getContext(), "Please choose date", Toast.LENGTH_SHORT).show();
            } else {
                selectedSeats.clear();
                Toast.makeText(getContext(), "Choose seats", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(getContext(), ChooseSeatsActivity.class);
//                intent.putExtra("movie", selectedMovie);
//                startActivity(intent);
                Fragment fragment = new Fragment();
                Class fragmentClass = ChooseSeatsFragment.class;
                try {
                    fragment = (Fragment) fragmentClass.newInstance();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("movie", selectedMovie);
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

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, selectedTime);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    public void book(View view) {
        BottomSheetDialog dialog = new BottomSheetDialog(getContext());
        dialog.setContentView(R.layout.dialog);
        TextView txtMovie = dialog.findViewById(R.id.title);
        ImageView img = dialog.findViewById(R.id.movieImage);
        TextView txtDay = dialog.findViewById(R.id.movieDate);
        TextView txtTime = dialog.findViewById(R.id.movieTime);
        TextView txtMovieSeat = dialog.findViewById(R.id.movieSeat);

        if (selectedMovie != null) {
            byte[] decodedString = android.util.Base64.decode(selectedMovie.getBase64Image(), android.util.Base64.DEFAULT);

            Glide.with(getContext())
                    .load(decodedString)
                    .into(img);
            txtMovie.setText(selectedMovie.getName());
            txtDay.setText(movieTime.getText().toString());
            txtTime.setText(spinner.getSelectedItem().toString());
            txtMovieSeat.setText(selectedSeats.toString());
            Button btnYes = dialog.findViewById(R.id.confirm);
            btnYes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (selectedSeats.size() == 0) {
                        Toast.makeText(getContext(), "Please choose seats", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (movieTime.getText().toString().equals("Time: dd/mm/yyyy")) {
                        Toast.makeText(getContext(), "Please choose date", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    dialog.dismiss();
                    if (!isBooked) {
                        Toast.makeText(getContext(), "Booked Successfully", Toast.LENGTH_SHORT).show();
                        //random text and number
                        String code = new Random().nextInt(999999) + selectedMovie.getName();
                        booking.setId(myRef.push().getKey());
                        booking.setName(selectedMovie.getName());
                        booking.setDate(movieTime.getText().toString());
                        booking.setTime(spinner.getSelectedItem().toString());
                        booking.setSeat(selectedSeats.toString());
                        booking.setImage(selectedMovie.getBase64Image());
                        booking.setCodeQR(code);
                        booking.setIdUser(uID);
                        createQRCode(code);
                    } else {
                        btn.setText("Book");
                        wv.loadUrl("about:blank");
                        wv.setVisibility(View.GONE);
                        Toast.makeText(getContext(), "Canceled Successfully", Toast.LENGTH_SHORT).show();
                        isBooked = false;
                    }
                }
            });
            dialog.show();
        }
    }
    private void createQRCode(String text) {
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(text,
                    BarcodeFormat.QR_CODE, 600, 600);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            ImageView imageView = new ImageView(getContext());
            imageView.setImageBitmap(bitmap);
            builder.setView(imageView);
            //btn ok
            builder.setPositiveButton("OK", (dialog, which) -> {
                Toast.makeText(getContext(), "Booked Successfully", Toast.LENGTH_SHORT).show();
                movieTime.setText("Time: dd/mm/yyyy");
                spinner.setSelection(0);
                quantity.setText("1");
                myRef.child(booking.getId()).setValue(booking);
                Fragment fragment = new Fragment();
                Class fragmentClass = TicketFragment.class;
                try {
                    fragment = (Fragment) fragmentClass.newInstance();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("text", text);
                    fragment.setArguments(bundle);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (fragment != null) {
                    FragmentManager fragmentManager = getParentFragmentManager();
                    fragmentManager.beginTransaction().setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                            .replace(R.id.frameLayout, fragment).commit();
                }
                dialog.dismiss();
            });
            builder.show();

        } catch (WriterException e) {
            e.printStackTrace();
        }
    }
}

