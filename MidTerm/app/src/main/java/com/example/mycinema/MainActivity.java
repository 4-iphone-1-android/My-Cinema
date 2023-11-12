package com.example.mycinema;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;


import androidx.recyclerview.widget.RecyclerView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import com.example.mycinema.setupdata.setUpData;
import com.google.android.gms.auth.api.signin.internal.Storage;

import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static ArrayList<Movie> movieList = new ArrayList<>();
    public static ArrayList<Movie> trendingMovies = new ArrayList<>();
    private String selectedFilter = "all";
    private FirebaseAuth storage;
    private DatabaseReference myRef;
    private setUpData mySetupData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SearchView searchView = findViewById(R.id.movieSearchView);
        searchView.clearFocus();
        searchView.setOnQueryTextFocusChangeListener((v, hasFocus) -> openSearchBarActivity());
//        setUpData();
        setUpBrowseCategories();
        setUpTrendingFilm();
        ImageView heartButton = findViewById(R.id.showFavoriteButton);
        heartButton.setOnClickListener(this);
        mySetupData = new setUpData(this);
        mySetupData.pushMovies(movieList);

        storage = FirebaseAuth.getInstance();
        FirebaseDatabase firebaseDatabase = storage.getCurrentUser();
    }


    private void setUpTrendingFilm(){
        RecyclerView recyclerView = findViewById(R.id.trendingList);
        CategoryAdapter adapter = new CategoryAdapter(this, trendingMovies);
        recyclerView.setAdapter(adapter);
    }
    private void setUpBrowseCategories() {
        RecyclerView recyclerView = findViewById(R.id.categoryList);
        CategoryAdapter adapter = new CategoryAdapter(this, movieList);
        recyclerView.setAdapter(adapter);
    }

//    private void setUpData() {
//        if(movieList.isEmpty()){
//            //Action
//            Movie pic_1 = new Movie("0", "Godzilla: King of the Monsters",
//                    R.drawable.picture1, "action", "The crypto-zoological agency " +
//                    "Monarch faces off against a battery of god-sized monsters, including the mighty" +
//                    " Godzilla, who collides with Mothra, Rodan, and his ultimate nemesis, the three-headed King Ghidorah.",
//                    4.2, "https://www.youtube.com/watch?v=QFxN2oDKk0E&pp=ygUmZ29kemlsbGEga2luZyBvZiB0aGUgbW9uc3RlcnMgdHJhaWxlciA%3D");
//            movieList.add(pic_1);
//
//            Movie pic_2 = new Movie("1", "Aquaman: Home is calling",
//                    R.drawable.picture2,
//                    "action",
//                    "Arthur Curry, the human-born heir to the underwater kingdom of Atlantis," +
//                            " goes on a quest to prevent a war between the worlds of ocean and land.",
//                    3.6, "https://www.youtube.com/watch?v=WDkg3h8PCVU&pp=ygUgYXF1YW1hbiBob21lIGlzIGNhbGxpbmcgdHJhaWxlciA%3D");
//            movieList.add(pic_2);
//
//            Movie pic_3 = new Movie("2", "Avengers: Endgame",
//                    R.drawable.picture3, "action",
//                    "After the devastating events of Avengers: Infinity War, " +
//                            "the universe is in ruins due to the efforts of the Mad Titan, Thanos. With the help of remaining allies, the Avengers must assemble once more in order to undo Thanos' actions and restore order to the universe once and for all, no matter what consequences may be in store.",
//                    4.5, "https://www.youtube.com/watch?v=TcMBFSGVi1c&pp=ygUZYXZlbmdlcnMgZW5kZ2FtZSB0cmFpbGVyIA%3D%3D");
//            movieList.add(pic_3);
//
//            Movie pic_4 = new Movie("3", "Avatar: The Way of Water",
//                    R.drawable.picture4, "action",
//                    "Set more than a decade after the events of the first film, learn the story of the Sully family (Jake, Neytiri, and their kids), " +
//                            "the trouble that follows them, the lengths they go to keep each other safe, the battles they fight to stay alive, and the tragedies they endure.",
//                    4.6, "https://www.youtube.com/watch?v=d9MyW72ELq0&pp=ygUgYXZhdGFyIHRoZSB3YXkgb2Ygd2F0ZXIgdHJhaWxlciA%3D");
//            movieList.add(pic_4);
//
//            Movie pic_5 = new Movie("4", "Mortal Kombat", R.drawable.picture5,
//                    "action",
//                    "Washed-up MMA fighter Cole Young, unaware of his heritage, and hunted by Emperor Shang Tsung's best warrior, Sub-Zero, seeks out and trains with Earth's greatest champions as he prepares to stand against the enemies of Outworld in a high stakes battle for the universe.",
//                    4.3, "https://www.youtube.com/watch?v=NYH2sLid0Zc&pp=ygUVbW9ydGFsIGtvbWJhdCB0cmFpbGVy");
//            movieList.add(pic_5);
//
//            Movie pic_6 = new Movie("5", "Good Boys", R.drawable.picture6,
//                    "comedy", "Three 6th grade boys ditch school and embark on an epic journey while carrying accidentally stolen drugs, being hunted by teenage girls, and trying to make their way home in time for a long-awaited party.",
//                    4.3, "https://www.youtube.com/watch?v=zPXqwAGmX04&pp=ygURZ29vZCBib3lzIHRyYWlsZXI%3D");
//            movieList.add(pic_6);
//
//            Movie pic_7 = new Movie("6", "Shazam!", R.drawable.picture7, "comedy",
//                    "A newly fostered young boy in search of his mother instead finds unexpected superpowers and soon gains a powerful enemy.", 4.1, "https://www.youtube.com/watch?v=go6GEIrcvFY&pp=ygUOc2hhemFtIHRyYWlsZXI%3D");
//            movieList.add(pic_7);
//
//            Movie pic_8 = new Movie("7", "Jumanji: Welcome to the Jungle",
//                    R.drawable.picture8, "comedy", "Four teenagers are sucked into a magical video game, and the only way they can escape is to work together to finish the game.",
//                    4.4, "https://www.youtube.com/watch?v=2QKg5SZ_35I&pp=ygUmanVtYW5qaSB3ZWxjb21lIHRvIHRoZSBqdW5nbGUgdHJhaWxlciA%3D");
//            movieList.add(pic_8);
//
//            Movie pic_9 = new Movie("8", "Thor: Ragnarok", R.drawable.picture9,
//                    "comedy", "Imprisoned on the planet Sakaar, Thor must race against time to return to Asgard and stop Ragnarök, the destruction of his world, at the hands of the powerful and ruthless villain Hela.",
//                    4.3, "https://www.youtube.com/watch?v=v7MGUNV8MxU&pp=ygUWdGhvciByYWduYXJvayB0cmFpbGVyIA%3D%3D");
//            movieList.add(pic_9);
//
//            Movie pic_10 = new Movie("9", "ted", R.drawable.picture10,
//                    "comedy", "John Bennett, a man whose childhood wish of bringing his teddy bear to life came true, now must decide between keeping the relationship with the bear, Ted or his girlfriend, Lori.",
//                    3.7, "https://www.youtube.com/watch?v=9fbo_pQvU7M&pp=ygULdGVkIHRyYWlsZXI%3D");
//            movieList.add(pic_10);
//
//            Movie pic_11 = new Movie("10", "Love at First Sight", R.drawable.picture11,
//                    "drama", "Hadley and Oliver begin to fall for each other on their flight from New York to London. The probability of ever finding each other again seems impossible, but love - and London - may have a way of defying the odds.",
//                    4.0, "https://www.youtube.com/watch?v=j0kro6SuwxM&pp=ygUbbG92ZSBhdCBmaXJzdCBzaWdodCB0cmFpbGVy");
//            movieList.add(pic_11);
//
//            Movie pic_12 = new Movie("11", "Past Lives", R.drawable.picture12,
//                    "drama", "Nora and Hae Sung, two deeply connected childhood friends, are wrested apart after Nora's family emigrated from South Korea. Twenty years later, they are reunited for one fateful week as they confront notions of love and destiny.",
//                    4.6, "https://www.youtube.com/watch?v=kA244xewjcI&pp=ygUSUGFzdCBMaXZlcyB0cmFpbGVy");
//            movieList.add(pic_12);
//
//            Movie pic_13 = new Movie("12", "After Everything", R.drawable.picture13,
//                    "drama", "After breaking up with his true love, best-selling author Hardin Scott travels to Portugal in an attempt to make amends for his past behavior.",
//                    3.9, "https://www.youtube.com/watch?v=fR1VptShVmg&pp=ygUYQWZ0ZXIgRXZlcnl0aGluZyB0cmFpbGVy");
//            movieList.add(pic_13);
//
//            Movie pic_14 = new Movie("13", "Titanic", R.drawable.picture14, "drama",
//                    "A seventeen-year-old aristocrat falls in love with a kind but poor artist aboard the luxurious, ill-fated R.M.S. Titanic.",
//                    3.5, "https://www.youtube.com/watch?v=kVrqfYjkTdQ&pp=ygUPdGl0YW5pYyB0cmFpbGVy");
//            movieList.add(pic_14);
//
//            Movie pic_15 = new Movie("14", "Twilight", R.drawable.picture15,
//                    "drama", "When Bella Swan moves to a small town in the Pacific Northwest, she falls in love with Edward Cullen, a mysterious classmate who reveals himself to be a 108-year-old vampire.",
//                    3.6, "https://www.youtube.com/watch?v=uxjNDE2fMjI&pp=ygUQdHdpbGlnaHQgdHJhaWxlcg%3D%3D");
//            movieList.add(pic_15);
//
//            Movie pic_16 = new Movie("15", "The Nun", R.drawable.picture16,
//                    "horror", "A priest with a haunted past and a novice on the threshold of her final vows are sent by the Vatican to investigate the death of a young nun in Romania and confront a malevolent force in the form of a demonic nun.",
//                    2.6, "https://www.youtube.com/watch?v=pzD9zGcUNrw&pp=ygUPdGhlIG51biB0cmFpbGVy");
//            movieList.add(pic_16);
//
//            Movie pic_17 = new Movie("16", "Saw", R.drawable.picture17, "horror",
//                    "Two strangers awaken in a room with no recollection of how they got there, and soon discover they're pawns in a deadly game perpetrated by a notorious serial killer..", 4.2,
//                    "https://www.youtube.com/watch?v=zaANSeQ3La4&pp=ygUQc2F3IDIwMDQgdHJhaWxlcg%3D%3D");
//            movieList.add(pic_17);
//
//            Movie pic_18 = new Movie("17", "The Conjuring", R.drawable.picture18,
//                    "horror", "Paranormal investigators Ed and Lorraine Warren work to help a family terrorized by a dark presence in their farmhouse.",
//                    4.2, "https://www.youtube.com/watch?v=k10ETZ41q5o&pp=ygUVdGhlIGNvbmp1cmluZyB0cmFpbGVy");
//            movieList.add(pic_18);
//
//            Movie pic_19 = new Movie("18", "The Meg", R.drawable.picture19,
//                    "horror", "A group of scientists exploring the Marianas Trench encounter the largest marine predator that has ever existed - the Megalodon.",
//                    2.2, "https://www.youtube.com/watch?v=udm5jUA-2bs&pp=ygUPdGhlIG1lZyB0cmFpbGVy");
//            movieList.add(pic_19);
//
//            Movie pic_20 = new Movie("19", "X", R.drawable.picture20, "horror",
//                    "In 1979, a group of young filmmakers set out to make an adult film in rural Texas, but when their reclusive, elderly hosts catch them in the act, the cast find themselves fighting for their lives.",
//                    3.8, "https://www.youtube.com/watch?v=Awg3cWuHfoc&pp=ygUJWCB0cmFpbGVy");
//            movieList.add(pic_20);
//
//            Movie pic_21 = new Movie("20", "Belle", R.drawable.picture21, "anime",
//                    "Suzu is a shy high school student living in a rural village. For years, she has only been a shadow of herself. But when she enters U, a massive virtual world, she escapes into her online persona as Belle, a globally-beloved singer.",
//                    4.8, "https://www.youtube.com/watch?v=izIycj3j4Ow&pp=ygUNQmVsbGUgdHJhaWxlcg%3D%3D");
//            movieList.add(pic_21);
//
//            Movie pic_22 = new Movie("21", "Spirited Away", R.drawable.picture22,
//                    "anime", "During her family's move to the suburbs, a sullen 10-year-old girl wanders into a world ruled by gods, witches and spirits, a world where humans are changed into beasts.",
//                    4.8, "https://www.youtube.com/watch?v=ByXuk9QqQkk&pp=ygUVU3Bpcml0ZWQgQXdheSB0cmFpbGVy");
//            movieList.add(pic_22);
//
//            Movie pic_23 = new Movie("22", "Grave of the Fireflies", R.drawable.picture23,
//                    "anime", "The story of Seita and Setsuko, two young Japanese siblings, living in the declining days of World War II. When an American firebombing separates the two children from their parents, the two siblings must rely completely on one another while they struggle to fight for their survival.",
//                    4.8,	"https://www.youtube.com/watch?v=4vPeTSRd580&t=10s&pp=ygUeZ3JhdmUgb2YgdGhlIGZpcmVmbGllcyB0cmFpbGVy");
//            movieList.add(pic_23);
//
//            Movie pic_24 = new Movie("23", "Your Name", R.drawable.picture24,
//                    "anime", "Two teenagers share a profound, magical connection upon discovering they are swapping bodies. Things manage to become even more complicated when the boy and girl decide to meet in person.",
//                    4.8, "https://www.youtube.com/watch?v=NooIc3dMncc&pp=ygURWW91ciBOYW1lIHRyYWlsZXI%3D");
//            movieList.add(pic_24);
//
//            Movie pic_25 = new Movie("24", "A Silent Voice: The Movie",
//                    R.drawable.picture25, "anime", "A young man is ostracized by his classmates after he bullies a deaf girl to the point where she moves away. Years later, he sets off on a path for redemption.",
//                    4.7, "https://www.youtube.com/watch?v=nfK6UgLra7g&pp=ygUhQSBTaWxlbnQgVm9pY2U6IFRoZSBNb3ZpZSB0cmFpbGVy");
//            movieList.add(pic_25);
//
//        }
//        trendingMovies.add(movieList.get(15));
//        trendingMovies.add(movieList.get(16));
//        trendingMovies.add(movieList.get(17));
//        trendingMovies.add(movieList.get(18));
//        trendingMovies.add(movieList.get(19));
//    }


    private void filterList(String status){
        selectedFilter = status;

        ArrayList<Movie> filteredMovie = new ArrayList<Movie>();

        for (Movie movie : MainActivity.movieList) {
            if (movie.getGenre().equalsIgnoreCase(status)) {
                filteredMovie.add(movie);
            }
        }
        CategoryAdapter adapter = new CategoryAdapter(this, filteredMovie);
        RecyclerView recyclerView = findViewById(R.id.categoryList);
        recyclerView.setAdapter(adapter);
    }

    public void actionFilterTapped(View view) {
        filterList("action");
    }

    public void comedyFilterTapped(View view) {
        filterList("comedy");
    }

    public void dramaFilterTapped(View view) {
        filterList("drama");
    }

    public void animeFilterTapped(View view) {
        filterList("anime");
    }

    public void horrorFilterTapped(View view) {
        filterList("horror");
    }

    private void openSearchBarActivity() {
        Intent intent = new Intent(this, SearchBar.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, FavoriteList.class);
        startActivity(intent);
    }

}
