package org.vedruna.filmapi.dto;

import org.vedruna.filmapi.persistance.model.Film;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PatchFilmNameDto {
    String name;

    public PatchFilmNameDto(Film film) {
        this.name = film.getFilmName();
    }
}
