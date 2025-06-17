package com.example.blogbackend.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogStats {

    private Integer commentsCount = 0;
    private Integer likesCount = 0;
    private Integer viewsCount = 0;
}
