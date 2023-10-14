package com.example.mycinema;

import java.io.Serializable;

public class Movie implements Serializable {
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Movie {
    private String id;
    private String name;
    private int image;

    private String genre;
    private String description;
    private double rating;
    private String trailerURL;

    public Movie(String id, String name, int image, String genre) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.genre = genre;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getGenre(){ return genre;}

    public void setGenre(){ this.genre = genre;}

    public String getDescription() {
        String description = null;
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getRating() {
        double rating = 0;
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getTrailerURL() {
        String trailerURL = null;
        return trailerURL;
    }

    public void setTrailerURL(String trailerURL) {
        this.trailerURL = trailerURL;
    }

    public String getTrailerID() {
        // Check if the url is valid and contains youtube.com or youtu.be
        if (trailerURL == null || (!trailerURL.contains("youtube.com") && !trailerURL.contains("youtu.be"))){
            return null;
        }
        // Extract the ID from the url using regex
        // The ID is usually after "v=" or "youtu.be/"
        // The ID is 11 characters long and can contain alphanumeric characters, dashes and underscores
        Pattern pattern = Pattern.compile("(?<=v=|youtube.com/)[a-zA-Z0-9_-]{11}");
        Matcher matcher = pattern.matcher(trailerURL);
        if (matcher.find()) {
            return matcher.group();
        } else {
            return null;
        }

    }
}
