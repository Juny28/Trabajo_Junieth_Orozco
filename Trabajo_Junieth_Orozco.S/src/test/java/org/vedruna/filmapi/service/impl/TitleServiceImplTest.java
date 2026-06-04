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

        when(watchmodeService.searchTitles(name)).thenReturn(apiResults);

        List<TitleDto> result = titleService.searchTitles(name);

        assertEquals(1, result.size());
        assertEquals("Inception", result.get(0).getTitle());
        verify(titleRepository, never()).findByTitleContainingIgnoreCase(any());
    }

    @Test
    void searchTitles_ShouldFallbackToLocal_WhenApiIsEmpty() {
        String name = "Local";
        when(watchmodeService.searchTitles(name)).thenReturn(new ArrayList<>());
        Title localTitle = new Title();
        localTitle.setTitle("Local Title");
        when(titleRepository.findByTitleContainingIgnoreCase(name)).thenReturn(List.of(localTitle));
        when(titleConverter.toDto(localTitle)).thenReturn(new TitleDto());

        List<TitleDto> result = titleService.searchTitles(name);

        assertFalse(result.isEmpty());
        verify(titleRepository, times(1)).findByTitleContainingIgnoreCase(name);
    }

    @Test
    void addFavorite_ShouldFetchFromWatchmode_WhenNotInDb() {
        String username = "user";
        Integer wmId = 123;
        User user = new User();
        user.setFavorites(new ArrayList<>());
        
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(titleRepository.findByWatchmodeId(wmId)).thenReturn(Optional.empty());
        
        TitleDto apiDetails = new TitleDto();
        apiDetails.setTitle("New Title");
        when(watchmodeService.getTitleDetails(wmId)).thenReturn(apiDetails);
        
        Title titleEntity = new Title();
        when(titleConverter.toEntity(apiDetails)).thenReturn(titleEntity);
        when(titleRepository.save(titleEntity)).thenReturn(titleEntity);

        titleService.addFavorite(username, wmId);

        assertTrue(user.getFavorites().contains(titleEntity));
        verify(titleRepository, times(1)).save(titleEntity);
    }

    @Test
    void addFavorite_ShouldThrowException_WhenUserNotFound() {
        when(userRepository.findByUsername("none")).thenReturn(Optional.empty());
        assertThrows(org.vedruna.filmapi.exception.UserNotFoundException.class, 
            () -> titleService.addFavorite("none", 123));
    }

    @Test
    void getUserFavorites_ShouldReturnList() {
        String username = "user";
        User user = new User();
        Title title = new Title();
        user.setFavorites(List.of(title));
        
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(titleConverter.toDto(title)).thenReturn(new TitleDto());

        List<TitleDto> result = titleService.getUserFavorites(username);

        assertEquals(1, result.size());
    }
}
