package com.example.blogbackend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateBlogPostRequestDto {

    @NotBlank(message = "Title cannot be blank")
    @Size(min = 5, max = 255, message = "Title must be between 5 and 255 characters")
    private String title;

    @NotBlank(message = "Content cannot be blank")
    private String content;

    private String image; // Optional

    private List<String> tags; // Optional

    @NotBlank(message = "Status cannot be blank (e.g., DRAFT, PUBLISHED)")
    private String status;
}
