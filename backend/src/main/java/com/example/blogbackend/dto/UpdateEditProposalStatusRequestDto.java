package com.example.blogbackend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateEditProposalStatusRequestDto {

    @NotBlank(message = "Status cannot be blank")
    @Pattern(regexp = "ACCEPTED|REJECTED", message = "Status must be either ACCEPTED or REJECTED")
    private String status;
}
