package com.example.blogbackend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateEditProposalRequestDto {

    @NotNull(message = "Blog Post ID cannot be null")
    private Long blogPostId;

    @NotBlank(message = "Proposed content cannot be blank")
    private String proposedContent;

    @NotBlank(message = "Reason for proposal cannot be blank")
    private String reason;
}
