package com.example.blog.service;

import com.example.blog.model.Author;
import com.example.blog.model.BlogPost;
import com.example.blog.model.Stats;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BlogService {
    private final Map<String, BlogPost> posts = new LinkedHashMap<>();

    @PostConstruct
    public void init() {
        // Seed with sample data similar to mockData.ts
        BlogPost p1 = new BlogPost(
                "1",
                "Building Scalable React Applications with TypeScript",
                new Author("Sarah Chen", "", 5),
                "In this comprehensive guide, we'll explore how to build scalable React applications using TypeScript. We'll cover best practices for component architecture, state management patterns, and how to structure your codebase for maintainability. TypeScript provides excellent tooling support and helps catch errors early in development.",
                "https://images.unsplash.com/photo-1633356122544-f134324a6cee?w=800&h=400&fit=crop",
                "2 hours ago",
                Arrays.asList("React", "TypeScript", "Frontend", "Architecture"),
                new Stats(23, 156, 1240),
                2
        );

        BlogPost p2 = new BlogPost(
                "2",
                "The Future of AI in Software Development",
                new Author("Marcus Johnson", "", 7),
                "Artificial Intelligence is revolutionizing how we write, test, and maintain code. From code completion tools like GitHub Copilot to automated testing frameworks, AI is becoming an indispensable part of the developer toolkit. Let's explore what the future holds for AI-assisted development.",
                null,
                "5 hours ago",
                Arrays.asList("AI", "Machine Learning", "Development", "Future"),
                new Stats(45, 289, 2100),
                null
        );

        posts.put(p1.getId(), p1);
        posts.put(p2.getId(), p2);
    }

    public Collection<BlogPost> findAll() {
        return posts.values();
    }

    public BlogPost findById(String id) {
        return posts.get(id);
    }

    public BlogPost save(BlogPost post) {
        if (post.getId() == null || post.getId().isEmpty()) {
            post.setId(UUID.randomUUID().toString());
        }
        posts.put(post.getId(), post);
        return post;
    }

    public void delete(String id) {
        posts.remove(id);
    }
}
