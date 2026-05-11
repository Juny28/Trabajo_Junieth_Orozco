package org.vedruna.filmapi.converter;

import org.springframework.stereotype.Component;
import org.vedruna.filmapi.dto.UserDto;
import org.vedruna.filmapi.persistence.model.User;
import java.util.stream.Collectors;

@Component
public class UserConverter {
    private final TitleConverter titleConverter;
    private final ReviewConverter reviewConverter;

    public UserConverter(TitleConverter titleConverter, ReviewConverter reviewConverter) {
        this.titleConverter = titleConverter;
        this.reviewConverter = reviewConverter;
    }

    public UserDto toDto(User user) {
        UserDto dto = new UserDto();
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        if (user.getFavorites() != null) {
            dto.setFavorites(user.getFavorites().stream().map(titleConverter::toDto).collect(Collectors.toList()));
        }
        if (user.getReviews() != null) {
            dto.setReviews(user.getReviews().stream().map(reviewConverter::toDto).collect(Collectors.toList()));
        }
        return dto;
    }
}
