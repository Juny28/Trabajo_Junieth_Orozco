package org.vedruna.filmapi.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.vedruna.filmapi.dto.PatchFilmNameDto;
import org.vedruna.filmapi.persistance.model.Film;
import org.vedruna.filmapi.persistance.repository.FilmRepository;
import org.vedruna.filmapi.service.FilmService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@Service
public class FilmServiceImpl implements FilmService {

    private final FilmRepository filmRepository;

    @Override
    public Page<Film> selectAllFilms(Pageable pageable) {
        log.info("Vamos a buscar todas las películas");
        return filmRepository.findAll(pageable);
    }

    @Override
    public Film insertFilm(Film film) {
        log.info("Vamos a crear una película");
        log.info("Parámetros de entrada: {}", film);
        return filmRepository.save(film);
    }

    @Override
    public Page<Film> selectFilmsByReleaseDate(LocalDate fromDate, LocalDate toDate, Pageable pageable) {
        log.info("Vamos a buscar las películas por fecha de lanzamiento" + 
                    "desde {} hasta {}", fromDate, toDate);
        return filmRepository.findByReleaseDateBetween(fromDate, toDate, pageable);
    }

    @Override
    public Film selectFilmById(Integer filmId) {
        log.info("Vamos a buscar la película con id {}", filmId);
        return filmRepository.findById(filmId).orElseThrow(
            () -> new RuntimeException("No se ha encontrado la película con id " + filmId)
        );
    }

    @Override
    public Film updateFilm(Integer filmId, Film film) {
        log.info("Vamos a actualizar la película con id {}", filmId);
        Film filmEntity = filmRepository.findById(filmId).orElseThrow(
            () -> new RuntimeException("No se ha encontrado la película con id " + filmId)
        );
        filmEntity.setFilmName(film.getFilmName());
        filmEntity.setReleaseDate(film.getReleaseDate());
        return filmRepository.save(filmEntity);
    }

    @Override
    public Film updateFilmName(Integer filmId, PatchFilmNameDto film) {
        log.info("Vamos a actualizar el nombre de la película con id {}", filmId);
        Film filmEntity = filmRepository.findById(filmId).orElseThrow(
            () -> new RuntimeException("No se ha encontrado la película con id " + filmId)
        );
        filmEntity.setFilmName(film.getName());
        return filmRepository.save(filmEntity);
    }

    @Override
    public void deleteFilm(Integer filmId) {
        log.info("Vamos a borrar la pelicula con id {}", filmId);
        filmRepository.deleteById(filmId);
    }

    @Override
    public Film selectFilmByIdOptionalCase(Integer filmId) {
        log.info("Vamos a buscar la película con id {}", filmId);
        Optional<Film> filmOpt = filmRepository.findById(filmId);

        if (filmOpt.isPresent()) {
            return filmOpt.get();
        } else {
            log.warn("No se ha encontrado la película con id {}", filmId);
            //ESTO ES SIMPLEMENTE DIDACTICO, NO USAR JAMÁS, DEBE SALTAR EXCEPCIÓN
            return null;
        }
    }

    
    
}
