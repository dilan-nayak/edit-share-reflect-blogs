package com.example.blogbackend.controller;

import com.example.blogbackend.dto.CreateEditProposalRequestDto;
import com.example.blogbackend.dto.EditProposalResponseDto;
// Import UpdateEditProposalStatusRequestDto if needed for status updates via a dedicated DTO, though current plan uses path variables for accept/reject.
import com.example.blogbackend.exception.UnauthorizedOperationException;
import com.example.blogbackend.model.User;
import com.example.blogbackend.repository.UserRepository;
import com.example.blogbackend.service.EditProposalService;
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

import java.util.Objects;

@RestController
@RequestMapping("/api") // Base path for these proposal-related endpoints
@CrossOrigin(origins = "*", maxAge = 3600) // Configure CORS as needed
public class EditProposalController {

    @Autowired
    private EditProposalService editProposalService;

    @Autowired
    private UserRepository userRepository; // For user ID check in getProposalsByUser

    // POST /api/posts/{postId}/proposals - Create a new edit proposal for a blog post
    @PostMapping("/posts/{postId}/proposals")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<EditProposalResponseDto> createEditProposal(
            @PathVariable Long postId, // postId from path
            @Valid @RequestBody CreateEditProposalRequestDto requestDto,
            Authentication authentication) {

        // Ensure the blogPostId in DTO matches the postId from path, or set it.
        // This reconciles the DTO design with the path variable.
        if (requestDto.getBlogPostId() == null) {
            requestDto.setBlogPostId(postId);
        } else if (!Objects.equals(requestDto.getBlogPostId(), postId)) {
            // Or throw a custom exception for mismatch
            return ResponseEntity.badRequest().build();
        }

        String userEmail = authentication.getName();
        EditProposalResponseDto createdProposal = editProposalService.createEditProposal(requestDto, userEmail);
        return new ResponseEntity<>(createdProposal, HttpStatus.CREATED);
    }

    // GET /api/posts/{postId}/proposals - Get all proposals for a specific blog post
    @GetMapping("/posts/{postId}/proposals")
    public ResponseEntity<Page<EditProposalResponseDto>> getProposalsForBlogPost(
            @PathVariable Long postId,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<EditProposalResponseDto> proposals = editProposalService.getProposalsForBlogPost(postId, pageable);
        return ResponseEntity.ok(proposals);
    }

    // GET /api/users/{userId}/proposals - Get all proposals submitted by a specific user
    @GetMapping("/users/{userId}/proposals")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Page<EditProposalResponseDto>> getProposalsByUser(
            @PathVariable Long userId,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            Authentication authentication) {

        String authenticatedUserEmail = authentication.getName();
        User authenticatedUser = userRepository.findByEmail(authenticatedUserEmail)
            .orElseThrow(() -> new UnauthorizedOperationException("Authenticated user not found."));

        if (!authenticatedUser.getId().equals(userId)) {
            // Potentially allow admins in the future with @PreAuthorize("hasRole('ADMIN')") or similar
            throw new UnauthorizedOperationException("User not authorized to view proposals of another user.");
        }

        Page<EditProposalResponseDto> proposals = editProposalService.getProposalsByUser(userId, pageable);
        return ResponseEntity.ok(proposals);
    }

    // GET /api/dashboard/proposals - Get pending proposals for the authenticated author's posts
    @GetMapping("/dashboard/proposals")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Page<EditProposalResponseDto>> getPendingProposalsForAuthor(
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            Authentication authentication) {
        String authorEmail = authentication.getName();
        Page<EditProposalResponseDto> proposals = editProposalService.getPendingProposalsForAuthor(authorEmail, pageable);
        return ResponseEntity.ok(proposals);
    }

    // PUT /api/proposals/{proposalId}/accept - Accept an edit proposal
    @PutMapping("/proposals/{proposalId}/accept")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<EditProposalResponseDto> acceptEditProposal(
            @PathVariable Long proposalId,
            Authentication authentication) {
        String blogAuthorEmail = authentication.getName();
        EditProposalResponseDto acceptedProposal = editProposalService.acceptEditProposal(proposalId, blogAuthorEmail);
        return ResponseEntity.ok(acceptedProposal);
    }

    // PUT /api/proposals/{proposalId}/reject - Reject an edit proposal
    @PutMapping("/proposals/{proposalId}/reject")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<EditProposalResponseDto> rejectEditProposal(
            @PathVariable Long proposalId,
            Authentication authentication) {
        String blogAuthorEmail = authentication.getName();
        EditProposalResponseDto rejectedProposal = editProposalService.rejectEditProposal(proposalId, blogAuthorEmail);
        return ResponseEntity.ok(rejectedProposal);
    }
}
