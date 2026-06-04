package org.vedruna.filmapi.dto;

import lombok.Data;

@Data
public class TitleDto {
    private Long id;
    private Integer watchmodeId;
    private String title;
    private String type;
    private Integer year;
    private String genre;
    private String poster;
}
