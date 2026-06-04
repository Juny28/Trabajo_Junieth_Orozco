package org.vedruna.filmapi.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.vedruna.filmapi.converter.TitleConverter;
import org.vedruna.filmapi.dto.TitleDto;
import org.vedruna.filmapi.persistence.model.Title;
import org.vedruna.filmapi.persistence.model.User;
import org.vedruna.filmapi.persistence.repository.TitleRepository;
import org.vedruna.filmapi.persistence.repository.UserRepository;
import org.vedruna.filmapi.service.TitleService;
import org.vedruna.filmapi.service.WatchmodeService;

import java.util.List;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

/**
 * Implementación del servicio para gestionar títulos (películas/series) y los favoritos de los usuarios.
 */
@Slf4j
@Service
public class TitleServiceImpl implements TitleService {
    private final WatchmodeService watchmodeService;
    private final TitleRepository titleRepository;
    private final UserRepository userRepository;
    private final TitleConverter titleConverter;

    public TitleServiceImpl(WatchmodeService watchmodeService, TitleRepository titleRepository,
                            UserRepository userRepository, TitleConverter titleConverter) {
        this.watchmodeService = watchmodeService;
        this.titleRepository = titleRepository;
        this.userRepository = userRepository;
        this.titleConverter = titleConverter;
    }

    /**
     * Searches for titles by name using the Watchmode API.
     * @param name the title name to search for
     * @return a list of TitleDto
     */
    @Override
    public List<TitleDto> searchTitles(String name) {
        log.info("Searching for titles with name: '{}'", name);
        
        // Prioritize Watchmode API to get fresh results
        List<TitleDto> apiResults = new java.util.ArrayList<>();
        try {
            apiResults = watchmodeService.searchTitles(name);
        } catch (Exception e) {
            log.warn("Watchmode API search failed: {}", e.getMessage());
        }

        if (!apiResults.isEmpty()) {
            log.info("Returning {} results from Watchmode API", apiResults.size());
            return apiResults;
        }

        // Fallback to local database if API returned nothing or failed
        log.info("No API results, falling back to local database for: '{}'", name);
        List<Title> localTitles;
        if (name == null || name.trim().isEmpty()) {
            localTitles = titleRepository.findAll();
        } else {
            localTitles = titleRepository.findByTitleContainingIgnoreCase(name);
        }

        return localTitles.stream().map(titleConverter::toDto).collect(Collectors.toList());
    }

    /**
     * Adds a title to the user's favorites list.
     * @param username the username
     * @param watchmodeId the Watchmode ID of the title
     */
    @Override
    @Transactional
    public void addFavorite(String username, Integer watchmodeId) {
        log.info("Adding title {} to favorites of user {}", watchmodeId, username);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    log.error("User {} not found", username);
                    return new org.vedruna.filmapi.exception.UserNotFoundException(username);
                });
        
        Title title = java.util.Objects.requireNonNull(titleRepository.findByWatchmodeId(watchmodeId)
                .orElseGet(() -> {
                    log.info("Title {} not in local database, fetching from Watchmode", watchmodeId);
                    TitleDto details = watchmodeService.getTitleDetails(watchmodeId);
                    if (details == null) {
                        log.error("Title {} not found in Watchmode API", watchmodeId);
                        throw new org.vedruna.filmapi.exception.TitleNotFoundException(String.valueOf(watchmodeId));
                    }
                    return titleRepository.save(titleConverter.toEntity(details));
                }));

        if (!user.getFavorites().contains(title)) {
            user.getFavorites().add(title);
            userRepository.save(user);
            log.info("Title {} added to favorites of user {}", watchmodeId, username);
        } else {
            log.warn("Title {} is already in favorites of user {}", watchmodeId, username);
        }
    }

    /**
     * Removes a title from the user's favorites list.
     * @param username the username
     * @param watchmodeId the Watchmode ID of the title
     */
    @Override
    @Transactional
    public void removeFavorite(String username, Integer watchmodeId) {
        log.info("Removing title {} from favorites of user {}", watchmodeId, username);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new org.vedruna.filmapi.exception.UserNotFoundException(username));
        
        Title title = titleRepository.findByWatchmodeId(watchmodeId)
                .orElseThrow(() -> {
                    log.error("Title {} not found in local database", watchmodeId);
                    return new org.vedruna.filmapi.exception.TitleNotFoundException("Title not found in favorites: " + watchmodeId);
                });

        user.getFavorites().remove(title);
        userRepository.save(user);
        log.info("Title {} removed from favorites of user {}", watchmodeId, username);
    }

    /**
     * Gets all favorite titles for a user.
     * @param username the username
     * @return a list of TitleDto
     */
    @Override
    public List<TitleDto> getUserFavorites(String username) {
        log.info("Fetching favorites for user: {}", username);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new org.vedruna.filmapi.exception.UserNotFoundException(username));
        return user.getFavorites().stream().map(titleConverter::toDto).collect(Collectors.toList());
    }
}
