package org.vedruna.filmapi.dto;

import lombok.Data;
import java.util.List;

@Data
public class UserDto {
    private String username;
    private String email;
    private List<TitleDto> favorites;
    private List<ReviewDto> reviews;
}
