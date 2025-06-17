package com.example.blogbackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "edit_proposals")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EditProposal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(nullable = false, columnDefinition = "TEXT")
    private String originalContent;

    @Lob
    @Column(nullable = false, columnDefinition = "TEXT")
    private String proposedContent;

    @Column(nullable = false)
    private String reason;

    @Column(nullable = false)
    private String status; // e.g., "PENDING", "ACCEPTED", "REJECTED"

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blog_post_id", nullable = false)
    private BlogPost blogPost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User proposedBy;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
