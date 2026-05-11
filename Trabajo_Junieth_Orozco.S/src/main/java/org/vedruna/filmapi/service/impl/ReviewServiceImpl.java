package org.vedruna.filmapi.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.vedruna.filmapi.converter.ReviewConverter;
import org.vedruna.filmapi.converter.TitleConverter;
import org.vedruna.filmapi.dto.ReviewCreateDto;
import org.vedruna.filmapi.dto.ReviewDto;
import org.vedruna.filmapi.dto.TitleDto;
import org.vedruna.filmapi.persistence.model.Review;
import org.vedruna.filmapi.persistence.model.Title;
import org.vedruna.filmapi.persistence.model.User;
import org.vedruna.filmapi.persistence.repository.ReviewRepository;
import org.vedruna.filmapi.persistence.repository.TitleRepository;
import org.vedruna.filmapi.persistence.repository.UserRepository;
import org.vedruna.filmapi.service.ReviewService;
import org.vedruna.filmapi.service.WatchmodeService;

import java.util.List;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

/**
 * Service implementation for managing reviews.
 */
@Slf4j
@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final TitleRepository titleRepository;
    private final UserRepository userRepository;
    private final WatchmodeService watchmodeService;
    private final ReviewConverter reviewConverter;
    private final TitleConverter titleConverter;

    public ReviewServiceImpl(ReviewRepository reviewRepository, TitleRepository titleRepository,
                             UserRepository userRepository, WatchmodeService watchmodeService,
                             ReviewConverter reviewConverter, TitleConverter titleConverter) {
        this.reviewRepository = reviewRepository;
        this.titleRepository = titleRepository;
        this.userRepository = userRepository;
        this.watchmodeService = watchmodeService;
        this.reviewConverter = reviewConverter;
        this.titleConverter = titleConverter;
    }

    /**
     * Gets all reviews for a specific title.
     * @param watchmodeId the Watchmode ID of the title
     * @return a list of ReviewDto
     */
    @Override
    public List<ReviewDto> getReviewsByTitle(Integer watchmodeId) {
        log.info("Fetching reviews for title: {}", watchmodeId);
        Title title = titleRepository.findByWatchmodeId(watchmodeId)
                .orElseThrow(() -> {
                    log.error("Title {} not found", watchmodeId);
                    return new RuntimeException("Title not found");
                });
        return reviewRepository.findByTitle(title).stream()
                .map(reviewConverter::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Creates a new review for a title.
     * @param username the username of the author
     * @param reviewDto the review data
     */
    @Override
    @Transactional
    public void createReview(String username, ReviewCreateDto reviewDto) {
        log.info("User {} is creating a review for title {}", username, reviewDto.getWatchmodeId());
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        Title title = titleRepository.findByWatchmodeId(reviewDto.getWatchmodeId())
                .orElseGet(() -> {
                    log.info("Title {} not in local database, fetching from Watchmode", reviewDto.getWatchmodeId());
                    TitleDto details = watchmodeService.getTitleDetails(reviewDto.getWatchmodeId());
                    if (details == null) throw new RuntimeException("Title not found in Watchmode");
                    return titleRepository.save(titleConverter.toEntity(details));
                });

        Review review = new Review();
        review.setText(reviewDto.getText());
        review.setRating(reviewDto.getRating());
        review.setUser(user);
        review.setTitle(title);
        reviewRepository.save(review);
        log.info("Review created with ID: {}", review.getId());
    }

    /**
     * Updates an existing review.
     * @param username the username of the author (for authorization)
     * @param reviewId the review ID
     * @param reviewDto the updated data
     */
    @Override
    @Transactional
    public void updateReview(String username, Long reviewId, ReviewCreateDto reviewDto) {
        log.info("User {} is updating review {}", username, reviewId);
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));
        
        if (!review.getUser().getUsername().equals(username)) {
            log.error("User {} is not authorized to edit review {}", username, reviewId);
            throw new RuntimeException("Unauthorized to edit this review");
        }

        review.setText(reviewDto.getText());
        review.setRating(reviewDto.getRating());
        reviewRepository.save(review);
        log.info("Review {} updated", reviewId);
    }

    /**
     * Deletes a review.
     * @param username the username of the author (for authorization)
     * @param reviewId the review ID
     */
    @Override
    @Transactional
    public void deleteReview(String username, Long reviewId) {
        log.info("User {} is deleting review {}", username, reviewId);
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));
        
        if (!review.getUser().getUsername().equals(username)) {
            log.error("User {} is not authorized to delete review {}", username, reviewId);
            throw new RuntimeException("Unauthorized to delete this review");
        }

        reviewRepository.delete(review);
        log.info("Review {} deleted", reviewId);
    }
}
