package org.vedruna.filmapi.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.vedruna.filmapi.dto.PatchFilmNameDto;
import org.vedruna.filmapi.persistance.model.Film;
import org.vedruna.filmapi.service.FilmService;

import lombok.AllArgsConstructor;

@CrossOrigin
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/films")
public class FilmController {
    
    private final FilmService filmService;

    @GetMapping("/")
    public Page<Film> getAllFilms(Pageable pageable) {
        return filmService.selectAllFilms(pageable);
    }

    @PostMapping("/")
    public Film createFilm(@RequestBody Film film) {
        return filmService.insertFilm(film);
    }

    @GetMapping("/release_date")
    public Page<Film> getFilmsByReleaseDate(@RequestParam LocalDate fromDate, @RequestParam LocalDate toDate, Pageable pageable) {
        return filmService.selectFilmsByReleaseDate(fromDate, toDate, pageable);
    }

    @GetMapping("/{filmId}")
    public Film getFilmById(@PathVariable Integer filmId) {
        return filmService.selectFilmById(filmId);
    }

    @PutMapping("/{filmId}")
    public Film editFilm(@PathVariable Integer filmId, @RequestBody Film film) {
        return filmService.updateFilm(filmId,film);
    }

    @PatchMapping("/{filmId}")
    public Film editFilmName(@PathVariable Integer filmId, @RequestBody PatchFilmNameDto film) {
        return filmService.updateFilmName(filmId, film);
    }

    @DeleteMapping("/{filmId}")
    public void deleteFilm(@PathVariable Integer filmId) {
        filmService.deleteFilm(filmId);
    }

    @GetMapping("/optional/{filmId}")
    public Film getFilmByIdOptionalCase(@PathVariable Integer filmId) {
        return filmService.selectFilmByIdOptionalCase(filmId);

    }
}
