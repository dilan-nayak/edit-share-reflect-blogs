package com.example.blogbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EditProposalResponseDto {
    private Long id;
    private Long blogPostId;
    private String blogPostTitle;
    private Long proposedByUserId;
    private String proposedByUserName;
    private String originalContent;
    private String proposedContent;
    private String reason;
    private String status;
    private LocalDateTime createdAt;
}
