package org.vedruna.filmapi.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class ReviewDto {
    private Long id;
    private String text;
    private Integer rating;
    private String username;
    private String title;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
