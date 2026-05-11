package org.vedruna.filmapi.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.vedruna.filmapi.dto.PatchFilmNameDto;
import org.vedruna.filmapi.persistance.model.Film;

public interface FilmService {
    Page<Film> selectAllFilms(Pageable pageable);
    Film insertFilm(Film film);
    Page<Film> selectFilmsByReleaseDate(LocalDate fromDate, LocalDate toDate, Pageable pageable);
    Film selectFilmById(Integer filmId);
    Film updateFilm(Integer filmId, Film film);
    Film updateFilmName(Integer filmId, PatchFilmNameDto film);
    void deleteFilm(Integer filmId);
    Film selectFilmByIdOptionalCase(Integer filmId);
}
