package com.example.mycinema;

public class ReadWriteMovie {
    public String id, name,image, genre, description, rating, trailerURL;

    public ReadWriteMovie(String id, String name, String image, String genre,
                                 String description, String rating, String trailerURL) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.genre = genre;
        this.description = description;
        this.rating = rating;
        this.trailerURL = trailerURL;
    }
}
