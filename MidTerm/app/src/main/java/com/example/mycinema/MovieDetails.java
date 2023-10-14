package com.example.mycinema;

import androidx.annotation.NonNull;

public class MovieDetails extends Movie {
    private String description;
    private double rating;
    private String trailerURL;

    public MovieDetails(String id, String name, int image, String genre, String description, double rating, String trailerURL) {
        super(id, name, image, genre);
        this.description = description;
        this.rating = rating;
        this.trailerURL = trailerURL;
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

    @NonNull
    @Override
    public String toString() {
        return "MovieWithDetails [id=" + getId() + ", name=" + getName() + ", image=" + getImage() + ", genre=" + getGenre()
                + ", description=" + description + ", rating=" + rating + ", trailerURL=" + trailerURL + "]";
    }

}
