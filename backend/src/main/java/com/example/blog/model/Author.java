package com.example.blog.model;

public class Author {
    private String name;
    private String avatar;
    private int level;

    public Author() {}

    public Author(String name, String avatar, int level) {
        this.name = name;
        this.avatar = avatar;
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
