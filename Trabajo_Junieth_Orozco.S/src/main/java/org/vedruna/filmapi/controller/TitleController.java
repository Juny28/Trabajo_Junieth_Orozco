package org.vedruna.filmapi.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.vedruna.filmapi.dto.TitleDto;
import org.vedruna.filmapi.service.TitleService;

import java.util.List;

@RestController
@RequestMapping("/titles")
@Tag(name = "Titles", description = "Endpoints for searching titles")
public class TitleController {
    private final TitleService titleService;

    public TitleController(TitleService titleService) {
        this.titleService = titleService;
    }

    @GetMapping("/search")
    public List<TitleDto> search(
            @RequestParam(required = false, defaultValue = "") String name,
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "8") int limit) {
        return titleService.searchTitles(name);
    }
}
