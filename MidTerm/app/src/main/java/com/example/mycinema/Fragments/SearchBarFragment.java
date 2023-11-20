package com.example.mycinema.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.mycinema.Movie;
import com.example.mycinema.MovieAdapter;
import com.example.mycinema.R;

import java.util.ArrayList;


public class SearchBarFragment extends Fragment {


    private ListView listView;
    private ImageView btn;
    public SearchBarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_search_bar, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btn = view.findViewById(R.id.backButton);
        initSearchWidgets(view);
        setUpList(view);
        setUpOnclickListener();

        btn.setOnClickListener(v -> {
            Fragment fragment = new Fragment();
            Class fragmentClass = HomeFragment.class;
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
        });
    }

    private void initSearchWidgets(View view) {
        SearchView searchView = view.findViewById(R.id.movieSearchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                ArrayList<Movie> filteredMovie = new ArrayList<Movie>();
                for (Movie movie : HomeFragment.movieList) {
                    if (movie.getName().toLowerCase().contains(s.toLowerCase())) {
                        filteredMovie.add(movie);
                    }
                }
                if (s.equals("")) {
                    filteredMovie.clear();
                }
                MovieAdapter adapter = new MovieAdapter(getContext(), 0,
                        filteredMovie);
                listView.setAdapter(adapter);
                return false;

            }
        });
    }

    public void setUpList(View view) {
        listView = view.findViewById(R.id.movieListView);
        MovieAdapter adapter = new MovieAdapter(getContext(), 0, HomeFragment.movieList);
        listView.setAdapter(adapter);
    }

    private void setUpOnclickListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Movie selectShape = (Movie) (listView.getItemAtPosition(position));
//                Intent showDetail = new Intent(getApplicationContext(), DetailActivity.class);
//                showDetail.putExtra("id", selectShape.getId());
//                startActivity(showDetail);
                Log.d("estPAi",">>>>>>>>");
                Fragment fragment = new Fragment();
                Class fragmentClass = DetailFragment.class;
                try {
                    fragment = (Fragment) fragmentClass.newInstance();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("movieID", selectShape.getId());
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
}