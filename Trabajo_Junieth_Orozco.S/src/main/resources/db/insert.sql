-- Initial test data
-- Roles are already inserted in schema.sql: (1, 'USER'), (2, 'ADMIN')

-- Passwords: "password"
INSERT INTO
    watchlist.users (
        id,
        username,
        email,
        password,
        created_at,
        role_id
    )
VALUES (
        1,
        "admin",
        "admin@localhost.com",
        "$2a$10$wU0v0Wv/x41vL5aO5/B4ue.8P6pDq0tGOWoH/4jQ/x1ZgWkW/Rz6G",
        NOW(),
        2
    ),
    (
        2,
        "Joaquin",
        "joaquin.borrego@vedruna.es",
        "$2a$10$wU0v0Wv/x41vL5aO5/B4ue.8P6pDq0tGOWoH/4jQ/x1ZgWkW/Rz6G",
        NOW(),
        1
    );

INSERT INTO
    watchlist.titles (
        id,
        watchmode_id,
        title,
        type,
        year,
        genre,
        poster
    )
VALUES (
        1,
        1406830,
        "The Matrix",
        "movie",
        1999,
        "Action",
        "https://m.media-amazon.com/images/M/MV5BNzQzOTk3OTAtNDQ0Zi00ZTVkLWI0MTEtMDllZjNkYzNjNTc4L2ltYWdlXkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1_QL75_UX500_CR0,47,500,740_.jpg"
    ),
    (
        2,
        3173903,
        "Inception",
        "movie",
        2010,
        "Sci-Fi",
        "https://m.media-amazon.com/images/M/MV5BMjAxMzY3NjcxNF5BMl5BanBnXkFtZTcwNTI5OTM0Mw@@._V1_QL75_UX500_CR0,0,500,740_.jpg"
    ),
    (
        3,
        1374536,
        "Interstellar",
        "movie",
        2014,
        "Sci-Fi",
        "https://m.media-amazon.com/images/M/MV5BZjdkOTU3MDktN2IxOS00OGEyLWFmMjktY2FiMmZkNWIyODZiXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_QL75_UX500_CR0,0,500,740_.jpg"
    ),
    (
        4,
        1146316,
        "Avatar",
        "movie",
        2009,
        "Action",
        "https://m.media-amazon.com/images/M/MV5BZDA0OGQxNTItMDZkMC00N2UyLTg3MzMtYTJmNjg3Nzk5MzRiXkEyXkFqcGdeQXVyMjUzOTY1NTc@._V1_QL75_UX500_CR0,0,500,740_.jpg"
    ),
    (
        5,
        1265324,
        "Titanic",
        "movie",
        1997,
        "Romance",
        "https://m.media-amazon.com/images/M/MV5BMDdmZGU3NDQtY2E5My00ZTliLWIzOTUtMTY4ZGI1YjdiNjk3XkEyXkFqcGdeQXVyNTA4NzY1MzY@._V1_QL75_UX500_CR0,0,500,740_.jpg"
    ),
    (
        7,
        1265326,
        "The Dark Knight",
        "movie",
        2008,
        "Action",
        "https://m.media-amazon.com/images/M/MV5BMTMxNTMwODM0NF5BMl5BanBnXkFtZTcwODAyMTk2Mw@@._V1_QL75_UX500_CR0,0,500,740_.jpg"
    ),
    (
        8,
        1265327,
        "Breaking Bad",
        "tv_series",
        2008,
        "Drama",
        "https://flxt.tmsimg.com/assets/p185846_b_v8_ad.jpg"
    ),
    (
        10,
        1265329,
        "Game of Thrones",
        "tv_series",
        2011,
        "Fantasy",
        "https://flxt.tmsimg.com/assets/p8553063_b_v13_ax.jpg"
    );

INSERT INTO
    watchlist.user_favorites (user_id, title_id)
VALUES (2, 1),
    (2, 3);

INSERT INTO
    watchlist.reviews (
        id,
        text,
        rating,
        created_at,
        updated_at,
        user_id,
        title_id
    )
VALUES (
        1,
        "Excelente película, muy buena trama.",
        5,
        NOW(),
        NOW(),
        2,
        1
    ),
    (
        2,
        "Me voló la cabeza.",
        5,
        NOW(),
        NOW(),
        2,
        2
    );