package org.vedruna.filmapi.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.vedruna.filmapi.dto.ReviewCreateDto;
import org.vedruna.filmapi.dto.ReviewDto;
import org.vedruna.filmapi.dto.TitleDto;
import org.vedruna.filmapi.persistence.model.Review;
import org.vedruna.filmapi.persistence.model.Title;
import org.vedruna.filmapi.persistence.model.User;
import org.vedruna.filmapi.persistence.repository.ReviewRepository;
import org.vedruna.filmapi.persistence.repository.TitleRepository;
import org.vedruna.filmapi.persistence.repository.UserRepository;
import org.vedruna.filmapi.service.WatchmodeService;
import org.vedruna.filmapi.converter.ReviewConverter;
import org.vedruna.filmapi.converter.TitleConverter;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceImplTest {

    @Mock
    private ReviewRepository reviewRepository;
    @Mock
    private TitleRepository titleRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private WatchmodeService watchmodeService;
    @Mock
    private ReviewConverter reviewConverter;
    @Mock
    private TitleConverter titleConverter;

    @InjectMocks
    private ReviewServiceImpl reviewService;

    @Test
    void getReviewsByTitle_ShouldReturnList_WhenTitleExists() {
        Integer wmId = 123;
        Title title = new Title();
        when(titleRepository.findByWatchmodeId(wmId)).thenReturn(Optional.of(title));
        when(reviewRepository.findByTitle(title)).thenReturn(List.of(new Review()));
        when(reviewConverter.toDto(any())).thenReturn(new ReviewDto());

        List<ReviewDto> result = reviewService.getReviewsByTitle(wmId);

        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    void createReview_ShouldSaveReview_WhenTitleInDb() {
        String username = "user";
        ReviewCreateDto dto = new ReviewCreateDto();
        dto.setWatchmodeId(123);
        dto.setText("Great movie");
        dto.setRating(9);

        User user = new User();
        Title title = new Title();
        
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(titleRepository.findByWatchmodeId(123)).thenReturn(Optional.of(title));

        reviewService.createReview(username, dto);

        verify(reviewRepository, times(1)).save(any(Review.class));
    }

    @Test
    void updateReview_ShouldUpdate_WhenOwner() {
        String username = "owner";
        Long reviewId = 1L;
        ReviewCreateDto dto = new ReviewCreateDto();
        dto.setText("Updated text");
        dto.setRating(10);

        Review review = new Review();
        User user = new User();
        user.setUsername(username);
        review.setUser(user);

        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(review));

        reviewService.updateReview(username, reviewId, dto);

        assertEquals("Updated text", review.getText());
        verify(reviewRepository, times(1)).save(review);
    }

    @Test
    void deleteReview_ShouldWork_WhenOwner() {
        String username = "owner";
        Long reviewId = 1L;
        Review review = new Review();
        User owner = new User();
        owner.setUsername(username);
        review.setUser(owner);

        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(review));

        reviewService.deleteReview(username, reviewId);

        verify(reviewRepository, times(1)).delete(review);
    }

    @Test
    void deleteReview_ShouldThrowException_WhenUnauthorized() {
        String username = "attacker";
        Long reviewId = 1L;
        Review review = new Review();
        User owner = new User();
        owner.setUsername("owner");
        review.setUser(owner);

        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(review));

        assertThrows(org.vedruna.filmapi.exception.UnauthorizedActionException.class, 
            () -> reviewService.deleteReview(username, reviewId));
    }
}
