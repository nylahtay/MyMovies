package com.example.mymovies;


public class Movie {
    private int id;
    private String title;
    private String url;
    private String original_language;
    private String desc;

    public Movie(int id, String title, String image, String lang, String desc){
        this.id = id;
        this.title = title;

            this.url = "https://image.tmdb.org/t/p/w500" + image + "";
        this.original_language = lang;
        this.desc = desc;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public String getDesc() {
        return desc;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}

