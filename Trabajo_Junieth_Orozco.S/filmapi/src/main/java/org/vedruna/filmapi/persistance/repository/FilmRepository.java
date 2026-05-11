package org.vedruna.filmapi.persistance.repository;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vedruna.filmapi.persistance.model.Film;

@Repository
public interface FilmRepository extends JpaRepository<Film, Integer>{
    //https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html
    Page<Film> findByReleaseDateBetween(LocalDate fromDate, LocalDate toDate, Pageable pageable);
} 
