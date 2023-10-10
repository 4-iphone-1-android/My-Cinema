package com.example.mycinema;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
        holder.movieImage.setImageResource(movie.getImage());
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView movieName;
        ImageView movieImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            movieName = itemView.findViewById(R.id.categoryName);
            movieImage = itemView.findViewById(R.id.categoryImage);
        }
    }
}
