package org.vedruna.filmapi.converter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.vedruna.filmapi.dto.ReviewDto;
import org.vedruna.filmapi.persistence.model.Review;
import org.vedruna.filmapi.persistence.model.Title;
import org.vedruna.filmapi.persistence.model.User;

import java.time.LocalDateTime;

@ExtendWith(MockitoExtension.class)
public class ReviewConverterTest {

    @InjectMocks
    private ReviewConverter converter;

    @Test
    void toDto_ShouldConvertReviewToDto() {
        Review review = new Review();
        review.setId(1L);
        review.setText("Excellent");
        review.setRating(10);
        review.setCreatedAt(LocalDateTime.now()); // Fixed to LocalDateTime
        
        User user = new User();
        user.setUsername("author");
        review.setUser(user);
        
        Title title = new Title();
        title.setTitle("Movie Title"); // Fixed to use title name
        review.setTitle(title);

        ReviewDto dto = converter.toDto(review);

        assertEquals("author", dto.getUsername());
        assertEquals("Movie Title", dto.getTitle());
        assertEquals("Excellent", dto.getText());
    }
}
