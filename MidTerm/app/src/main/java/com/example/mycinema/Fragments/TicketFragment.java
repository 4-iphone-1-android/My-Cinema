package com.example.mycinema.Fragments;

import static com.example.mycinema.LoginActivity.uID;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mycinema.BookActivity;
import com.example.mycinema.R;
import com.example.mycinema.TicketAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class TicketFragment extends Fragment {

    TextView ticketText;
    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference mDatabaseReference = mDatabase.getReference("Booking");
    private RecyclerView recyclerView;
    private List<BookActivity> bookingList = new ArrayList<>();
    public TicketFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ticket, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView=view.findViewById(R.id.ticket_recycler_view);


        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    bookingList.clear();
                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        BookActivity booking = postSnapshot.getValue(BookActivity.class);
                        assert booking != null;
                        if (booking.getIdUser().equals(uID)) {
                            bookingList.add(booking);
                        }

                    }
                    if(bookingList.size() > 0){
                        TicketAdapter ticketAdapter = new TicketAdapter(getContext(), bookingList);
                        recyclerView.setAdapter(ticketAdapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}