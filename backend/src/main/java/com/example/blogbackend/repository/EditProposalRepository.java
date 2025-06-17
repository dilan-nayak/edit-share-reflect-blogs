package com.example.blogbackend.repository;

import com.example.blogbackend.model.EditProposal;
import com.example.blogbackend.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EditProposalRepository extends JpaRepository<EditProposal, Long> {
    Page<EditProposal> findByBlogPostId(Long blogPostId, Pageable pageable);
    Page<EditProposal> findByProposedById(Long userId, Pageable pageable);
    Page<EditProposal> findByBlogPostAuthorAndStatus(User author, String status, Pageable pageable);
    // Add other custom query methods if needed
}
