package com.example.mycinema.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycinema.CategoryAdapter;
import com.example.mycinema.Movie;
import com.example.mycinema.ProfileActivity;
import com.example.mycinema.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class HomeFragment extends Fragment {

    public static ArrayList<Movie> movieList = new ArrayList<>();
    public static ArrayList<Movie> trendingMovies = new ArrayList<>();
    private String selectedFilter = "all";
    private DatabaseReference myRef;
    private FirebaseDatabase database;
    private Button actionBtn, comedyBtn, dramaBtn, animeBtn, horrorBtn;
    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        actionBtn = view.findViewById(R.id.actionBtn);
        comedyBtn = view.findViewById(R.id.comedyBtn);
        dramaBtn = view.findViewById(R.id.dramaBtn);
        animeBtn = view.findViewById(R.id.animeBtn);
        horrorBtn = view.findViewById(R.id.horrorBtn);

        SearchView searchView = view.findViewById(R.id.movieSearchView);
        searchView.clearFocus();
        searchView.setOnQueryTextFocusChangeListener((v, hasFocus) -> openSearchBarActivity());

        setUpBrowseCategories(view);
//        setUpTrendingFilm();

        actionBtn.setOnClickListener(view1 -> filterList("action", view));
        comedyBtn.setOnClickListener(view1 -> filterList("comedy", view));
        dramaBtn.setOnClickListener(view1 -> filterList("drama", view));
        animeBtn.setOnClickListener(view1 -> filterList("anime", view));
        horrorBtn.setOnClickListener(view1 -> filterList("horror", view));

        ImageView profileButton = view.findViewById(R.id.showProfileButton);
        profileButton.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), ProfileActivity.class);
            startActivity(intent);
        });
    }
    private void setUpData(){
        if (trendingMovies.size() == 0){
        trendingMovies.add(movieList.get(0));
        trendingMovies.add(movieList.get(12));
        trendingMovies.add(movieList.get(24));
        trendingMovies.add(movieList.get(3));
        trendingMovies.add(movieList.get(5));
        }
    }
    private void setUpBrowseCategories(View view) {
        ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.setCancelable(false);
        dialog.setTitle("Loading");
        dialog.setMessage("Wait a second");
        dialog.show();

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("movies");
        Query query = myRef.limitToFirst(25);
        RecyclerView trendingRecycler = view.findViewById(R.id.trendingList);
        RecyclerView recyclerView = view.findViewById(R.id.categoryList);

        CategoryAdapter adapter = new CategoryAdapter(getContext(), movieList);
        CategoryAdapter adapter2 = new CategoryAdapter(getContext(), trendingMovies);

        recyclerView.setAdapter(adapter);
        trendingRecycler.setAdapter(adapter2);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("TAGAPI", "onDataChange: " + dataSnapshot.getChildrenCount());
                movieList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Movie movie = postSnapshot.getValue(Movie.class);
                    movieList.add(movie);
                }
                if (adapter != null){
                    adapter.notifyDataSetChanged();
                }
                if (movieList.size() == 25){
                    setUpData();
                    adapter2.notifyDataSetChanged();
                }
                dialog.cancel();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("TAGAPI", "onCancelled: " + databaseError.getMessage());
            }
        });


    }

    private void filterList(String status , View view){
        selectedFilter = status;

        ArrayList<Movie> filteredMovie = new ArrayList<Movie>();

        for (Movie movie : HomeFragment.movieList) {
            if (movie.getGenre().equalsIgnoreCase(status)) {
                filteredMovie.add(movie);
            }
        }
        CategoryAdapter adapter = new CategoryAdapter(getContext(), filteredMovie);
        RecyclerView recyclerView = view.findViewById(R.id.categoryList);
        recyclerView.setAdapter(adapter);
    }


    private void openSearchBarActivity() {
        Fragment fragment = new Fragment();
        Class fragmentClass = SearchBarFragment.class;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (fragment != null) {
            FragmentManager fragmentManager = getParentFragmentManager();
            fragmentManager.beginTransaction().setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                    .replace(R.id.frameLayout, fragment).commit();
        }
    }

}