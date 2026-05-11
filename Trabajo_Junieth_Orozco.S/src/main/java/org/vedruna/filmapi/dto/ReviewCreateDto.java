package org.vedruna.filmapi.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReviewCreateDto {
    @NotBlank
    private String text;

    @NotNull
    @Min(1)
    @Max(10)
    private Integer rating;

    @NotNull
    private Integer watchmodeId;
}
