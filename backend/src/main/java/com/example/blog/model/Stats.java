package com.example.blog.model;

public class Stats {
    private int comments;
    private int likes;
    private int views;

    public Stats() {}

    public Stats(int comments, int likes, int views) {
        this.comments = comments;
        this.likes = likes;
        this.views = views;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }
}
