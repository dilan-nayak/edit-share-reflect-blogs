package com.example.blogbackend.repository;

import com.example.blogbackend.model.BlogPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogPostRepository extends JpaRepository<BlogPost, Long> {
    // Custom query methods for blog posts can be added here later
}
