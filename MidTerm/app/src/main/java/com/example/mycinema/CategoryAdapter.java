package com.example.mycinema;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private Context context;
    private List<Movie> movieList;

    public CategoryAdapter(Context context, List<Movie> movieList) {
        this.context = context;
        this.movieList = movieList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.category_item, parent,
                false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Movie movie = movieList.get(position);
        holder.movieName.setText(movie.getName());
        byte[] decodedString = android.util.Base64.decode(movie.getBase64Image(), android.util.Base64.DEFAULT);

        Glide.with(context)
                .load(decodedString)
                .into(holder.movieImage);

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                ArrayList<Movie> longFavor = DetailActivity.getFavoriteMovies();
                boolean flag = false;
                for (Movie i : longFavor) {
                    if (i.getName().equals(movie.getName())) {
                        flag = true;
                    }
                }
                if (!flag) {
                    longFavor.add(movie);
                    Toast.makeText(context, movie.getName() + " Adding to favourite list",
                            Toast.LENGTH_LONG).show();
                }
                return false;
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("movie", movie);
                context.startActivity(intent);
            }
        });

        holder.bookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start the BookActivity
                Intent intent = new Intent(context, Booking.class);

                // Add any extra data you want to pass to the BookActivity
                intent.putExtra("movie", movie); // Assuming you have an ID associated with each movie

                // Start the BookActivity
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView movieName;
        ImageView movieImage;
        AppCompatButton bookButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            movieName = itemView.findViewById(R.id.movieName);
            movieImage = itemView.findViewById(R.id.movieImage);
            bookButton = itemView.findViewById(R.id.book);
        }
    }
}
