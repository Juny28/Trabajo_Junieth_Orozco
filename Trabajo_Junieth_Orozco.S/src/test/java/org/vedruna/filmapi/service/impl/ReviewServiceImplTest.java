package org.vedruna.filmapi.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.vedruna.filmapi.dto.ReviewCreateDto;
import org.vedruna.filmapi.persistence.model.Review;
import org.vedruna.filmapi.persistence.model.Title;
import org.vedruna.filmapi.persistence.model.User;
import org.vedruna.filmapi.persistence.repository.ReviewRepository;
import org.vedruna.filmapi.persistence.repository.TitleRepository;
import org.vedruna.filmapi.persistence.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceImplTest {

    @Mock
    private ReviewRepository reviewRepository;
    @Mock
    private TitleRepository titleRepository;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ReviewServiceImpl reviewService;

    @Test
    void createReview_ShouldSaveReview_WhenValid() {
        User user = new User();
        user.setUsername("testuser");
        Title title = new Title();
        title.setWatchmodeId(123);
        ReviewCreateDto dto = new ReviewCreateDto();
        dto.setWatchmodeId(123);
        dto.setText("Good");
        dto.setRating(8);

        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        when(titleRepository.findByWatchmodeId(123)).thenReturn(Optional.of(title));

        reviewService.createReview("testuser", dto);

        verify(reviewRepository, times(1)).save(any(Review.class));
    }

    @Test
    void deleteReview_ShouldDelete_WhenAuthorized() {
        User user = new User();
        user.setUsername("testuser");
        Review review = new Review();
        review.setUser(user);

        when(reviewRepository.findById(1L)).thenReturn(Optional.of(review));

        reviewService.deleteReview("testuser", 1L);

        verify(reviewRepository, times(1)).delete(review);
    }

    @Test
    void deleteReview_ShouldThrowException_WhenUnauthorized() {
        User user = new User();
        user.setUsername("other");
        Review review = new Review();
        review.setUser(user);

        when(reviewRepository.findById(1L)).thenReturn(Optional.of(review));

        assertThrows(RuntimeException.class, () -> reviewService.deleteReview("testuser", 1L));
    }
}
