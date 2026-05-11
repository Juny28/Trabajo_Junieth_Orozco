package org.vedruna.filmapi.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.vedruna.filmapi.dto.TitleDto;
import org.vedruna.filmapi.persistence.model.Title;
import org.vedruna.filmapi.persistence.model.User;
import org.vedruna.filmapi.persistence.repository.TitleRepository;
import org.vedruna.filmapi.persistence.repository.UserRepository;
import org.vedruna.filmapi.service.WatchmodeService;
import org.vedruna.filmapi.converter.TitleConverter;

@ExtendWith(MockitoExtension.class)
public class TitleServiceImplTest {

    @Mock
    private TitleRepository titleRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private WatchmodeService watchmodeService;
    @Mock
    private TitleConverter titleConverter;

    @InjectMocks
    private TitleServiceImpl titleService;

    @Test
    void addFavorite_ShouldAddTitleToUser_WhenTitleExists() {
        User user = new User();
        user.setUsername("testuser");
        user.setFavorites(new ArrayList<>());
        Title title = new Title();
        title.setWatchmodeId(123);

        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        when(titleRepository.findByWatchmodeId(123)).thenReturn(Optional.of(title));

        titleService.addFavorite("testuser", 123);

        assertTrue(user.getFavorites().contains(title));
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void removeFavorite_ShouldRemoveTitleFromUser() {
        User user = new User();
        user.setUsername("testuser");
        Title title = new Title();
        title.setWatchmodeId(123);
        user.setFavorites(new ArrayList<>());
        user.getFavorites().add(title);

        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        when(titleRepository.findByWatchmodeId(123)).thenReturn(Optional.of(title));

        titleService.removeFavorite("testuser", 123);

        assertFalse(user.getFavorites().contains(title));
        verify(userRepository, times(1)).save(user);
    }
}
