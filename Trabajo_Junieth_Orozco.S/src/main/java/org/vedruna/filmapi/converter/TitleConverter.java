package org.vedruna.filmapi.converter;

import org.springframework.stereotype.Component;
import org.vedruna.filmapi.dto.TitleDto;
import org.vedruna.filmapi.persistence.model.Title;

/**
 * Conversor para mapear entre la entidad Title y TitleDto.
 */
@Component
public class TitleConverter {
    public TitleDto toDto(Title title) {
        TitleDto dto = new TitleDto();
        dto.setId(title.getId());
        dto.setWatchmodeId(title.getWatchmodeId());
        dto.setTitle(title.getTitle());
        dto.setType(title.getType());
        dto.setYear(title.getYear());
        dto.setGenre(title.getGenre());
        dto.setPoster(title.getPoster());
        return dto;
    }

    public Title toEntity(TitleDto dto) {
        Title title = new Title();
        title.setWatchmodeId(dto.getWatchmodeId());
        title.setTitle(dto.getTitle());
        title.setType(dto.getType());
        title.setYear(dto.getYear());
        title.setGenre(dto.getGenre());
        title.setPoster(dto.getPoster());
        return title;
    }
}
