package org.vedruna.filmapi.service;

import org.vedruna.filmapi.dto.TitleDto;
import java.util.List;

public interface WatchmodeService {
    List<TitleDto> searchTitles(String name);
    TitleDto getTitleDetails(Integer watchmodeId);
}
