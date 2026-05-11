package org.vedruna.filmapi.service;

import org.vedruna.filmapi.dto.TitleDto;
import java.util.List;

public interface TitleService {
    List<TitleDto> searchTitles(String name);
    void addFavorite(String username, Integer watchmodeId);
    void removeFavorite(String username, Integer watchmodeId);
    List<TitleDto> getUserFavorites(String username);
}
