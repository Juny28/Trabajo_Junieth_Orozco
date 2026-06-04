package org.vedruna.filmapi.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.ArrayList;
import java.util.List;

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

    @Test
    void searchTitles_ShouldPrioritizeApiResults_WhenApiReturnsData() {
        String name = "Inception";
        List<TitleDto> apiResults = new ArrayList<>();
        TitleDto dto = new TitleDto();
        dto.setTitle("Inception");
        apiResults.add(dto);

        // When API results are found
        when(watchmodeService.searchTitles(name)).thenReturn(apiResults);

        List<TitleDto> result = titleService.searchTitles(name);

        // It should return API results and NOT call the database search
        assertEquals(1, result.size());
        assertEquals("Inception", result.get(0).getTitle());
        verify(titleRepository, never()).findByTitleContainingIgnoreCase(any());
        verify(titleRepository, never()).findAll();
    }
}
