package com.example.mycinema;

import android.graphics.drawable.Drawable;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Movie implements Serializable {

    private String id;
    private String name;
    private int image;
    private String genre;
    private String description;
    private double rating;
    private String trailerURL;

    private String base64Image;

    public Drawable getRealImage() {
        return realImage;
    }

    public void setRealImage(Drawable realImage) {
        this.realImage = realImage;
    }

    private Drawable realImage;


    public Movie(String id, String name, int image, String genre, String description, double rating, String trailerURL) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.genre = genre;
        this.description = description;
        this.rating = rating;
        this.trailerURL = trailerURL;
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

    public String getGenre() {
        return genre;
    }

    public void setGenre() {
        this.genre = genre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getTrailerURL() {
        return trailerURL;
    }

    public void setTrailerURL(String trailerURL) {
        this.trailerURL = trailerURL;
    }

    public String getTrailerID() {
        if (trailerURL == null || (!trailerURL.contains("youtube.com") && !trailerURL.contains("youtu.be"))) {
            return null;
        }
        Pattern pattern = Pattern.compile("(?<=v=|youtube.com/)[a-zA-Z0-9_-]{11}");
        Matcher matcher = pattern.matcher(trailerURL);
        if (matcher.find()) {
            return matcher.group();
        } else {
            return null;
        }

    }
    public String getBase64Image() {
        return base64Image;
    }

    public void setBase64Image(String base64Image) {
        this.base64Image = base64Image;
    }
}
