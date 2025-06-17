package com.example.blogbackend.service;

import com.example.blogbackend.dto.CommentDto;
import com.example.blogbackend.dto.CreateCommentRequestDto;
import com.example.blogbackend.exception.ResourceNotFoundException;
import com.example.blogbackend.model.BlogPost;
import com.example.blogbackend.model.BlogStats;
import com.example.blogbackend.model.Comment;
import com.example.blogbackend.model.User;
import com.example.blogbackend.repository.BlogPostRepository;
import com.example.blogbackend.repository.CommentRepository;
import com.example.blogbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private BlogPostRepository blogPostRepository;

    @Autowired
    private UserRepository userRepository;

    // --- Helper DTO Mapping ---
    private CommentDto mapToCommentDto(Comment comment) {
        return new CommentDto(
                comment.getId(),
                comment.getContent(),
                comment.getAuthor().getId(),
                comment.getAuthor().getName(),
                comment.getBlogPost().getId(),
                comment.getCreatedAt()
        );
    }

    // --- Service Methods ---

    @Transactional
    public CommentDto createComment(Long postId, CreateCommentRequestDto requestDto, String userEmail) {
        User author = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", userEmail));
        BlogPost blogPost = blogPostRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("BlogPost", "id", postId));

        Comment comment = new Comment();
        comment.setContent(requestDto.getContent());
        comment.setAuthor(author);
        comment.setBlogPost(blogPost);

        Comment savedComment = commentRepository.save(comment);

        // Update commentsCount in BlogPost stats
        BlogStats stats = blogPost.getStats();
        if (stats == null) { // Should be initialized, but as a safeguard
            stats = new BlogStats();
        }
        stats.setCommentsCount(stats.getCommentsCount() + 1);
        blogPost.setStats(stats);
        blogPostRepository.save(blogPost); // Save updated BlogPost

        return mapToCommentDto(savedComment);
    }

    @Transactional(readOnly = true)
    public Page<CommentDto> getCommentsForPost(Long postId, Pageable pageable) {
        // Ensure post exists before fetching comments
        if (!blogPostRepository.existsById(postId)) {
            throw new ResourceNotFoundException("BlogPost", "id", postId);
        }
        Page<Comment> comments = commentRepository.findByBlogPostId(postId, pageable);
        return comments.map(this::mapToCommentDto);
    }
}
