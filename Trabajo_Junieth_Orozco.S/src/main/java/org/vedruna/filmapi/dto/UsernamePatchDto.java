package org.vedruna.filmapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UsernamePatchDto {
    @NotBlank
    @Size(min = 3, max = 20)
    private String username;
}
