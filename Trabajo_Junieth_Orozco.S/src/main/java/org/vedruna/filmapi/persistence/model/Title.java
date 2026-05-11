package org.vedruna.filmapi.persistence.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "titles")
public class Title {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "watchmode_id", unique = true, nullable = false)
    private Integer watchmodeId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String type; // Movie/TV Show

    @Column(nullable = false)
    private Integer year;

    @Column(nullable = false)
    private String genre;

    @OneToMany(mappedBy = "title", cascade = CascadeType.ALL)
    private List<Review> reviews;

    @ManyToMany(mappedBy = "favorites")
    private List<User> usersWhoFavorited;
}
