package com.example.mycinema;

public class Movie {
    private String id;
    private String name;
    private int image;

    private String genre;

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

}
