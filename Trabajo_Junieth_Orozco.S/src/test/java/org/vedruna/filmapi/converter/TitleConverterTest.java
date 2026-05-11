package org.vedruna.filmapi.converter;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.vedruna.filmapi.dto.TitleDto;
import org.vedruna.filmapi.persistence.model.Title;

public class TitleConverterTest {

    private final TitleConverter converter = new TitleConverter();

    @Test
    void toDto_ShouldMapFields() {
        Title entity = new Title();
        entity.setWatchmodeId(123);
        entity.setTitle("Test");
        entity.setType("movie");
        entity.setYear(2022);
        entity.setGenre("Action");

        TitleDto dto = converter.toDto(entity);

        assertEquals(123, dto.getWatchmodeId());
        assertEquals("Test", dto.getTitle());
    }

    @Test
    void toEntity_ShouldMapFields() {
        TitleDto dto = new TitleDto();
        dto.setWatchmodeId(123);
        dto.setTitle("Test");
        dto.setType("movie");
        dto.setYear(2022);
        dto.setGenre("Action");

        Title entity = converter.toEntity(dto);

        assertEquals(123, entity.getWatchmodeId());
        assertEquals("Test", entity.getTitle());
    }
}
