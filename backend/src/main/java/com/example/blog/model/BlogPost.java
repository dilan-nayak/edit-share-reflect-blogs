package com.example.blog.model;

import java.util.List;

public class BlogPost {
    private String id;
    private String title;
    private Author author;
    private String content;
    private String image;
    private String date;
    private List<String> tags;
    private Stats stats;
    private Integer proposedEdits;

    public BlogPost() {}

    public BlogPost(String id, String title, Author author, String content, String image, String date, List<String> tags, Stats stats, Integer proposedEdits) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.content = content;
        this.image = image;
        this.date = date;
        this.tags = tags;
        this.stats = stats;
        this.proposedEdits = proposedEdits;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public Stats getStats() {
        return stats;
    }

    public void setStats(Stats stats) {
        this.stats = stats;
    }

    public Integer getProposedEdits() {
        return proposedEdits;
    }

    public void setProposedEdits(Integer proposedEdits) {
        this.proposedEdits = proposedEdits;
    }
}
