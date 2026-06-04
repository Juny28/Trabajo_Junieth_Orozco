package org.vedruna.filmapi.converter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.vedruna.filmapi.dto.UserDto;
import org.vedruna.filmapi.persistence.model.Rol;
import org.vedruna.filmapi.persistence.model.User;

import java.util.Collections;

@ExtendWith(MockitoExtension.class)
public class UserConverterTest {

    @Mock
    private TitleConverter titleConverter;
    @Mock
    private ReviewConverter reviewConverter;

    @InjectMocks
    private UserConverter converter;

    @Test
    void toDto_ShouldConvertUserToDto() {
        User user = new User();
        user.setUsername("testuser");
        user.setEmail("test@test.com");
        user.setFavorites(Collections.emptyList());
        user.setReviews(Collections.emptyList());
        
        Rol rol = new Rol();
        rol.setName("USER");
        user.setRole(rol); // Fixed from setRoles to setRole

        UserDto dto = converter.toDto(user);

        assertEquals("testuser", dto.getUsername());
        assertEquals("test@test.com", dto.getEmail());
    }
}
