package com.example.blogbackend.controller;

import com.example.blogbackend.dto.BlogPostResponseDto;
import com.example.blogbackend.dto.CreateBlogPostRequestDto;
import com.example.blogbackend.dto.UpdateBlogPostRequestDto;
import com.example.blogbackend.service.BlogPostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
@CrossOrigin(origins = "*", maxAge = 3600) // Configure CORS as needed
public class BlogPostController {

    @Autowired
    private BlogPostService blogPostService;

    // GET /api/posts - Get all blog posts (paginated)
    @GetMapping
    public ResponseEntity<Page<BlogPostResponseDto>> getAllBlogPosts(
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<BlogPostResponseDto> posts = blogPostService.getAllBlogPosts(pageable);
        return ResponseEntity.ok(posts);
    }

    // GET /api/posts/{id} - Get a single blog post by ID
    @GetMapping("/{id}")
    public ResponseEntity<BlogPostResponseDto> getBlogPostById(@PathVariable Long id) {
        BlogPostResponseDto post = blogPostService.getBlogPostById(id);
        return ResponseEntity.ok(post);
    }

    // POST /api/posts - Create a new blog post
    @PostMapping
    @PreAuthorize("isAuthenticated()") // Ensures user is authenticated
    public ResponseEntity<BlogPostResponseDto> createBlogPost(
            @Valid @RequestBody CreateBlogPostRequestDto requestDto,
            Authentication authentication) {
        String userEmail = authentication.getName();
        BlogPostResponseDto createdPost = blogPostService.createBlogPost(requestDto, userEmail);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    // PUT /api/posts/{id} - Update an existing blog post
    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<BlogPostResponseDto> updateBlogPost(
            @PathVariable Long id,
            @Valid @RequestBody UpdateBlogPostRequestDto requestDto,
            Authentication authentication) {
        String userEmail = authentication.getName();
        BlogPostResponseDto updatedPost = blogPostService.updateBlogPost(id, requestDto, userEmail);
        return ResponseEntity.ok(updatedPost);
    }

    // DELETE /api/posts/{id} - Delete a blog post
    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> deleteBlogPost(
            @PathVariable Long id,
            Authentication authentication) {
        String userEmail = authentication.getName();
        blogPostService.deleteBlogPost(id, userEmail);
        return ResponseEntity.noContent().build();
    }

    // POST /api/posts/{id}/like - Like a blog post
    @PostMapping("/{id}/like")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<BlogPostResponseDto> likeBlogPost(
            @PathVariable Long id,
            Authentication authentication) {
        // The userEmail is extracted to ensure an authenticated user is performing the action,
        // even if the service method itself doesn't strictly need it for a simple like increment.
        String userEmail = authentication.getName();
        BlogPostResponseDto likedPost = blogPostService.likeBlogPost(id, userEmail);
        return ResponseEntity.ok(likedPost);
    }
}
