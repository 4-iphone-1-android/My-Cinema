package com.example.mycinema.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycinema.Movie;
import com.example.mycinema.R;
import com.example.mycinema.SeatAdapter;

import java.util.ArrayList;
import java.util.List;


public class ChooseSeatsFragment extends Fragment {


    List<String> seats = new ArrayList<>();
    Button btn;
    public ChooseSeatsFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_choose_seats, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btn = view.findViewById(R.id.next);
        for (int i = 1; i <= 30; i++) {
            if(i <= 10){
                seats.add("A " + i);
            }
            if(i <= 20 && i > 10){
                seats.add("B " + i);
            }
            if(i > 20){
                seats.add("C " + i);
            }
        }
        SeatAdapter adapterSeat = new SeatAdapter(seats);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 5));
        recyclerView.setAdapter(adapterSeat);
        btn.setOnClickListener(v -> {
            if(BookingFragment.selectedSeats.size() == 0){
                Toast.makeText(getContext(), "Haven't selected seats", Toast.LENGTH_SHORT).show();
            }
//            Intent intent = new Intent(this, BookingFragment.class);
//            intent.putExtra("movie", (Movie) getIntent().getSerializableExtra("movie"));
//            startActivity(intent);
            Fragment fragment = new Fragment();
            Class fragmentClass = BookingFragment.class;
            try {
                fragment = (Fragment) fragmentClass.newInstance();
                Bundle bundle = new Bundle();
                bundle.putSerializable("movie", (Movie) getArguments().getSerializable("movie"));
                fragment.setArguments(bundle);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (fragment != null) {
                FragmentManager fragmentManager = getParentFragmentManager();
                fragmentManager.beginTransaction().setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                        .replace(R.id.frameLayout, fragment).commit();
            }
        });
    }
}