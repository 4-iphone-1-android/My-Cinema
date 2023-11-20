package com.example.mycinema;

import static com.example.mycinema.Fragments.BookingFragment.selectedSeats;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SeatAdapter extends RecyclerView.Adapter<SeatAdapter.ViewHolder> {
    List<String> seats;

    public SeatAdapter(List<String> seats) {
        this.seats = seats;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.item_seat, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String seat = seats.get(position);
        holder.itemView.setTag(position);
        holder.seat.setText(seat);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedSeats.contains(seat)){
                    selectedSeats.remove(seat);
                    holder.seat.setBackgroundResource(R.drawable.seat2);
                    return;
                }
                selectedSeats.add(seat);
                Log.d("TAGKKKKKKKK", "onClick: " + selectedSeats);
                for (String s : selectedSeats) {
                    Log.d("TAGKKKKKKKK", "onBindViewHolder2: " + s);
                    if (s.equals(seat)) {
                        Log.d("TAGKKKKKKKK", "onBindViewHolder3: " + s);
                        holder.seat.setBackgroundResource(R.drawable.seat);
                    }
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return seats.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView seat;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            seat = itemView.findViewById(R.id.seat);
        }
    }
}
