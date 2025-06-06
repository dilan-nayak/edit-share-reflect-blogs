package com.example.blog.controller;

import com.example.blog.model.BlogPost;
import com.example.blog.service.BlogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/posts")
@CrossOrigin
public class BlogController {
    private final BlogService blogService;

    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @GetMapping
    public Collection<BlogPost> all() {
        return blogService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BlogPost> byId(@PathVariable String id) {
        BlogPost post = blogService.findById(id);
        return post != null ? ResponseEntity.ok(post) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public BlogPost create(@RequestBody BlogPost post) {
        return blogService.save(post);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BlogPost> update(@PathVariable String id, @RequestBody BlogPost post) {
        if (blogService.findById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        post.setId(id);
        return ResponseEntity.ok(blogService.save(post));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        if (blogService.findById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        blogService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
