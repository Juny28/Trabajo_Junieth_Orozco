package org.vedruna.filmapi.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vedruna.filmapi.persistence.model.Title;
import java.util.Optional;

@Repository
public interface TitleRepository extends JpaRepository<Title, Long> {
    Optional<Title> findByWatchmodeId(Integer watchmodeId);
    java.util.List<Title> findByTitleContainingIgnoreCase(String title);
}
