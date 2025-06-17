package com.example.blogbackend.service;

import com.example.blogbackend.dto.CreateEditProposalRequestDto;
import com.example.blogbackend.dto.EditProposalResponseDto;
import com.example.blogbackend.exception.IllegalActionException;
import com.example.blogbackend.exception.ResourceNotFoundException;
import com.example.blogbackend.exception.UnauthorizedOperationException;
import com.example.blogbackend.model.BlogPost;
import com.example.blogbackend.model.EditProposal;
import com.example.blogbackend.model.User;
import com.example.blogbackend.repository.BlogPostRepository;
import com.example.blogbackend.repository.EditProposalRepository;
import com.example.blogbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
public class EditProposalService {

    @Autowired
    private EditProposalRepository editProposalRepository;

    @Autowired
    private BlogPostRepository blogPostRepository;

    @Autowired
    private UserRepository userRepository;

    // --- Helper DTO Mapping ---
    private EditProposalResponseDto mapToEditProposalResponseDto(EditProposal proposal) {
        return new EditProposalResponseDto(
                proposal.getId(),
                proposal.getBlogPost().getId(),
                proposal.getBlogPost().getTitle(),
                proposal.getProposedBy().getId(),
                proposal.getProposedBy().getName(),
                proposal.getOriginalContent(),
                proposal.getProposedContent(),
                proposal.getReason(),
                proposal.getStatus(),
                proposal.getCreatedAt()
        );
    }

    // --- Service Methods ---

    @Transactional
    public EditProposalResponseDto createEditProposal(CreateEditProposalRequestDto requestDto, String userEmail) {
        User proposer = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", userEmail));
        BlogPost blogPost = blogPostRepository.findById(requestDto.getBlogPostId())
                .orElseThrow(() -> new ResourceNotFoundException("BlogPost", "id", requestDto.getBlogPostId()));

        EditProposal proposal = new EditProposal();
        proposal.setBlogPost(blogPost);
        proposal.setProposedBy(proposer);
        proposal.setOriginalContent(blogPost.getContent()); // Capture current content as original
        proposal.setProposedContent(requestDto.getProposedContent());
        proposal.setReason(requestDto.getReason());
        proposal.setStatus("PENDING"); // Initial status

        EditProposal savedProposal = editProposalRepository.save(proposal);
        return mapToEditProposalResponseDto(savedProposal);
    }

    @Transactional(readOnly = true)
    public Page<EditProposalResponseDto> getProposalsForBlogPost(Long postId, Pageable pageable) {
        if (!blogPostRepository.existsById(postId)) {
            throw new ResourceNotFoundException("BlogPost", "id", postId);
        }
        Page<EditProposal> proposals = editProposalRepository.findByBlogPostId(postId, pageable);
        return proposals.map(this::mapToEditProposalResponseDto);
    }

    @Transactional(readOnly = true)
    public Page<EditProposalResponseDto> getProposalsByUser(Long userId, Pageable pageable) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User", "id", userId);
        }
        Page<EditProposal> proposals = editProposalRepository.findByProposedById(userId, pageable);
        return proposals.map(this::mapToEditProposalResponseDto);
    }

    @Transactional(readOnly = true)
    public Page<EditProposalResponseDto> getPendingProposalsForAuthor(String authorEmail, Pageable pageable) {
        User author = userRepository.findByEmail(authorEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User (author)", "email", authorEmail));
        Page<EditProposal> proposals = editProposalRepository.findByBlogPostAuthorAndStatus(author, "PENDING", pageable);
        return proposals.map(this::mapToEditProposalResponseDto);
    }

    @Transactional
    public EditProposalResponseDto acceptEditProposal(Long proposalId, String blogAuthorEmail) {
        EditProposal proposal = editProposalRepository.findById(proposalId)
                .orElseThrow(() -> new ResourceNotFoundException("EditProposal", "id", proposalId));
        User blogAuthor = userRepository.findByEmail(blogAuthorEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User (author)", "email", blogAuthorEmail));

        if (!proposal.getBlogPost().getAuthor().getId().equals(blogAuthor.getId())) {
            throw new UnauthorizedOperationException("User not authorized to accept this proposal.");
        }
        if (!"PENDING".equals(proposal.getStatus())) {
            throw new IllegalActionException("Proposal is not in PENDING state, cannot accept. Current state: " + proposal.getStatus());
        }

        BlogPost blogPost = proposal.getBlogPost();
        // Add current content to versions
        if (blogPost.getVersions() == null) {
            blogPost.setVersions(new ArrayList<>());
        }
        blogPost.getVersions().add(blogPost.getContent());
        // Update blog post content
        blogPost.setContent(proposal.getProposedContent());
        blogPostRepository.save(blogPost);

        proposal.setStatus("ACCEPTED");
        EditProposal updatedProposal = editProposalRepository.save(proposal);
        return mapToEditProposalResponseDto(updatedProposal);
    }

    @Transactional
    public EditProposalResponseDto rejectEditProposal(Long proposalId, String blogAuthorEmail) {
        EditProposal proposal = editProposalRepository.findById(proposalId)
                .orElseThrow(() -> new ResourceNotFoundException("EditProposal", "id", proposalId));
        User blogAuthor = userRepository.findByEmail(blogAuthorEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User (author)", "email", blogAuthorEmail));

        if (!proposal.getBlogPost().getAuthor().getId().equals(blogAuthor.getId())) {
            throw new UnauthorizedOperationException("User not authorized to reject this proposal.");
        }
        if (!"PENDING".equals(proposal.getStatus())) {
            throw new IllegalActionException("Proposal is not in PENDING state, cannot reject. Current state: " + proposal.getStatus());
        }

        proposal.setStatus("REJECTED");
        EditProposal updatedProposal = editProposalRepository.save(proposal);
        return mapToEditProposalResponseDto(updatedProposal);
    }
}
