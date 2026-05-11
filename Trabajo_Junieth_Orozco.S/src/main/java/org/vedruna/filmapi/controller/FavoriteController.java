package org.vedruna.filmapi.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.vedruna.filmapi.dto.TitleDto;
import org.vedruna.filmapi.service.TitleService;

import java.util.List;

@RestController
@RequestMapping("/favorites")
@Tag(name = "Favorites", description = "Endpoints for managing user favorites")
public class FavoriteController {
    private final TitleService titleService;

    public FavoriteController(TitleService titleService) {
        this.titleService = titleService;
    }

    @PostMapping("/{watchmodeId}")
    public void addFavorite(@PathVariable Integer watchmodeId, @AuthenticationPrincipal UserDetails userDetails) {
        titleService.addFavorite(userDetails.getUsername(), watchmodeId);
    }

    @DeleteMapping("/{watchmodeId}")
    public void removeFavorite(@PathVariable Integer watchmodeId, @AuthenticationPrincipal UserDetails userDetails) {
        titleService.removeFavorite(userDetails.getUsername(), watchmodeId);
    }

    @GetMapping("/me")
    public List<TitleDto> getMyFavorites(@AuthenticationPrincipal UserDetails userDetails) {
        return titleService.getUserFavorites(userDetails.getUsername());
    }
}
