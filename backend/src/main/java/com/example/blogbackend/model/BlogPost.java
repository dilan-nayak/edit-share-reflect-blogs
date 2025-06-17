package com.example.blogbackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "blog_posts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Lob
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    private String image; // Nullable

    @ElementCollection(fetch = FetchType.EAGER) // Eager fetch for simpler scenarios, consider LAZY for performance
    @CollectionTable(name = "blog_post_tags", joinColumns = @JoinColumn(name = "blog_post_id"))
    @Column(name = "tag")
    private List<String> tags;

    @Embedded
    private BlogStats stats = new BlogStats(); // Initialize with default values

    @ElementCollection(fetch = FetchType.LAZY) // Lazy fetch for potentially large version history
    @CollectionTable(name = "blog_post_versions", joinColumns = @JoinColumn(name = "blog_post_id"))
    @Lob
    @Column(name = "content_version", columnDefinition = "TEXT")
    private List<String> versions;

    @Column(nullable = false)
    private String status; // e.g., "PUBLISHED", "DRAFT"

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @OneToMany(mappedBy = "blogPost", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EditProposal> editProposals;

    @OneToMany(mappedBy = "blogPost", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}
