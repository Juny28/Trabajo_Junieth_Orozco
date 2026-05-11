package org.vedruna.filmapi.persistance.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "films")
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "film_id", nullable = false, unique = true)
    Integer filmId;
    
    @Column(name = "film_name", nullable = false, unique = true, length = 150)
    String filmName;

    @Column(name = "release_date", nullable = false)
    LocalDate releaseDate;
}
