package org.vedruna.filmapi.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.vedruna.filmapi.dto.ReviewCreateDto;
import org.vedruna.filmapi.dto.ReviewDto;
import org.vedruna.filmapi.service.ReviewService;

import java.util.List;

@RestController
@RequestMapping
@Tag(name = "Reviews", description = "Endpoints for managing reviews")
public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/titles/{watchmodeId}/reviews")
    public List<ReviewDto> getReviews(@PathVariable Integer watchmodeId) {
        return reviewService.getReviewsByTitle(watchmodeId);
    }

    @PostMapping("/reviews")
    public void createReview(@Valid @RequestBody ReviewCreateDto reviewDto, @AuthenticationPrincipal UserDetails userDetails) {
        reviewService.createReview(userDetails.getUsername(), reviewDto);
    }

    @PutMapping("/reviews/{id}")
    public void updateReview(@PathVariable Long id, @Valid @RequestBody ReviewCreateDto reviewDto, @AuthenticationPrincipal UserDetails userDetails) {
        reviewService.updateReview(userDetails.getUsername(), id, reviewDto);
    }

    @DeleteMapping("/reviews/{id}")
    public void deleteReview(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        reviewService.deleteReview(userDetails.getUsername(), id);
    }
}
