package com.example.blogbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogPostResponseDto {
    private Long id;
    private String title;
    private String content;
    private String image;
    private List<String> tags;
    private String authorName;
    private Long authorId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private BlogStatsDto stats;
    private String status;
}
