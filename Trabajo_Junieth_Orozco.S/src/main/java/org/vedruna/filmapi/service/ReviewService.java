package org.vedruna.filmapi.service;

import org.vedruna.filmapi.dto.ReviewCreateDto;
import org.vedruna.filmapi.dto.ReviewDto;
import java.util.List;

public interface ReviewService {
    List<ReviewDto> getReviewsByTitle(Integer watchmodeId);
    void createReview(String username, ReviewCreateDto reviewDto);
    void updateReview(String username, Long reviewId, ReviewCreateDto reviewDto);
    void deleteReview(String username, Long reviewId);
}
