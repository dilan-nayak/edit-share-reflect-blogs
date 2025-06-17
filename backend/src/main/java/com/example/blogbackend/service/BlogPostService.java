package com.example.blogbackend.service;

import com.example.blogbackend.dto.BlogPostResponseDto;
import com.example.blogbackend.dto.BlogStatsDto;
import com.example.blogbackend.dto.CreateBlogPostRequestDto;
import com.example.blogbackend.dto.UpdateBlogPostRequestDto;
import com.example.blogbackend.exception.ResourceNotFoundException;
import com.example.blogbackend.exception.UnauthorizedOperationException;
import com.example.blogbackend.model.BlogPost;
import com.example.blogbackend.model.BlogStats;
import com.example.blogbackend.model.User;
import com.example.blogbackend.repository.BlogPostRepository;
import com.example.blogbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BlogPostService {

    @Autowired
    private BlogPostRepository blogPostRepository;

    @Autowired
    private UserRepository userRepository;

    // --- Helper Methods for DTO Mapping ---
    private BlogStatsDto mapToBlogStatsDto(BlogStats blogStats) {
        if (blogStats == null) {
            return new BlogStatsDto(0, 0, 0);
        }
        return new BlogStatsDto(
                blogStats.getCommentsCount(),
                blogStats.getLikesCount(),
                blogStats.getViewsCount()
        );
    }

    private BlogPostResponseDto mapToBlogPostResponseDto(BlogPost blogPost) {
        return new BlogPostResponseDto(
                blogPost.getId(),
                blogPost.getTitle(),
                blogPost.getContent(),
                blogPost.getImage(),
                blogPost.getTags(),
                blogPost.getAuthor().getName(),
                blogPost.getAuthor().getId(),
                blogPost.getCreatedAt(),
                blogPost.getUpdatedAt(),
                mapToBlogStatsDto(blogPost.getStats()),
                blogPost.getStatus()
        );
    }

    // --- Public Service Methods ---

    @Transactional(readOnly = true)
    public Page<BlogPostResponseDto> getAllBlogPosts(Pageable pageable) {
        Page<BlogPost> blogPosts = blogPostRepository.findAll(pageable);
        return blogPosts.map(this::mapToBlogPostResponseDto);
    }

    @Transactional
    public BlogPostResponseDto getBlogPostById(Long id) {
        BlogPost blogPost = blogPostRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("BlogPost", "id", id));

        // Increment views count
        BlogStats stats = blogPost.getStats();
        if (stats == null) { // Should be initialized in entity, but as a safeguard
            stats = new BlogStats();
        }
        stats.setViewsCount(stats.getViewsCount() + 1);
        blogPost.setStats(stats);
        blogPostRepository.save(blogPost); // Save the updated view count

        return mapToBlogPostResponseDto(blogPost);
    }

    @Transactional
    public BlogPostResponseDto createBlogPost(CreateBlogPostRequestDto requestDto, String userEmail) {
        User author = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", userEmail));

        BlogPost blogPost = new BlogPost();
        blogPost.setTitle(requestDto.getTitle());
        blogPost.setContent(requestDto.getContent());
        blogPost.setImage(requestDto.getImage());
        blogPost.setTags(requestDto.getTags() != null ? new ArrayList<>(requestDto.getTags()) : new ArrayList<>());
        blogPost.setStatus(requestDto.getStatus());
        blogPost.setAuthor(author);
        blogPost.setStats(new BlogStats(0, 0, 0)); // Initialize stats
        blogPost.setVersions(new ArrayList<>()); // Initialize versions list

        BlogPost savedPost = blogPostRepository.save(blogPost);
        return mapToBlogPostResponseDto(savedPost);
    }

    @Transactional
    public BlogPostResponseDto updateBlogPost(Long id, UpdateBlogPostRequestDto requestDto, String userEmail) {
        BlogPost blogPost = blogPostRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("BlogPost", "id", id));
        User currentUser = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", userEmail));

        if (!blogPost.getAuthor().getId().equals(currentUser.getId())) {
            throw new UnauthorizedOperationException("User not authorized to update this post.");
        }

        // Add current content to versions before updating
        if (blogPost.getVersions() == null) {
            blogPost.setVersions(new ArrayList<>());
        }
        blogPost.getVersions().add(blogPost.getContent());

        blogPost.setTitle(requestDto.getTitle());
        blogPost.setContent(requestDto.getContent());
        blogPost.setImage(requestDto.getImage());
        blogPost.setTags(requestDto.getTags() != null ? new ArrayList<>(requestDto.getTags()) : blogPost.getTags());
        blogPost.setStatus(requestDto.getStatus());

        BlogPost updatedPost = blogPostRepository.save(blogPost);
        return mapToBlogPostResponseDto(updatedPost);
    }

    @Transactional
    public void deleteBlogPost(Long id, String userEmail) {
        BlogPost blogPost = blogPostRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("BlogPost", "id", id));
        User currentUser = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", userEmail));

        if (!blogPost.getAuthor().getId().equals(currentUser.getId())) {
            // Consider allowing admins/moderators to delete posts in a real app
            throw new UnauthorizedOperationException("User not authorized to delete this post.");
        }
        blogPostRepository.delete(blogPost);
    }

    @Transactional
    public BlogPostResponseDto likeBlogPost(Long id, String userEmail) {
        BlogPost blogPost = blogPostRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("BlogPost", "id", id));

        // Ensure user exists, though not strictly needed for a simple like increment
        // In a real app, you'd check if this user has already liked the post.
        userRepository.findByEmail(userEmail)
            .orElseThrow(() -> new ResourceNotFoundException("User", "email", userEmail));


        BlogStats stats = blogPost.getStats();
        if (stats == null) {
            stats = new BlogStats();
        }
        stats.setLikesCount(stats.getLikesCount() + 1);
        blogPost.setStats(stats);

        BlogPost savedPost = blogPostRepository.save(blogPost);
        return mapToBlogPostResponseDto(savedPost);
    }
}
