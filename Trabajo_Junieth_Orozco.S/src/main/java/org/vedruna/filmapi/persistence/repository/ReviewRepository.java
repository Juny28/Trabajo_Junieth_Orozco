package org.vedruna.filmapi.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vedruna.filmapi.persistence.model.Review;
import org.vedruna.filmapi.persistence.model.Title;
import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByTitle(Title title);
}
