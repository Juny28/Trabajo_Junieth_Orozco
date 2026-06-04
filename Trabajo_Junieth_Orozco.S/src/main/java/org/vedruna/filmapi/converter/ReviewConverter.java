package org.vedruna.filmapi.converter;

import org.springframework.stereotype.Component;
import org.vedruna.filmapi.dto.ReviewDto;
import org.vedruna.filmapi.persistence.model.Review;

/**
 * Conversor para mapear entre la entidad Review y ReviewDto.
 */
@Component
public class ReviewConverter {
    public ReviewDto toDto(Review review) {
        ReviewDto dto = new ReviewDto();
        dto.setId(review.getId());
        dto.setText(review.getText());
        dto.setRating(review.getRating());
        dto.setUsername(review.getUser().getUsername());
        dto.setTitle(review.getTitle().getTitle());
        dto.setCreatedAt(review.getCreatedAt());
        dto.setUpdatedAt(review.getUpdatedAt());
        return dto;
    }
}
