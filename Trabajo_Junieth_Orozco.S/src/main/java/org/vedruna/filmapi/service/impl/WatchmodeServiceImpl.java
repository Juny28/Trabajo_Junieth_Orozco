package org.vedruna.filmapi.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.vedruna.filmapi.dto.TitleDto;
import org.vedruna.filmapi.service.WatchmodeService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

/**
 * Implementation of WatchmodeService that consumes the Watchmode API.
 */
@Slf4j
@Service
public class WatchmodeServiceImpl implements WatchmodeService {
    private final RestTemplate restTemplate;

    @Value("${watchmode.api.key:YOUR_API_KEY_HERE}")
    private String apiKey;

    private static final String SEARCH_URL = "https://api.watchmode.com/v1/search/?apiKey={apiKey}&search_field=name&search_value={name}";
    private static final String DETAILS_URL = "https://api.watchmode.com/v1/title/{id}/details/?apiKey={apiKey}";

    public WatchmodeServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Searches for titles by name using the Watchmode API.
     * @param name the title name to search for
     * @return a list of TitleDto
     */
    @Override
    public List<TitleDto> searchTitles(String name) {
        log.info("Searching titles with name: {}", name);
        ResponseEntity<Map<String, Object>> responseEntity = restTemplate.exchange(
                SEARCH_URL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Map<String, Object>>() {},
                apiKey,
                name
        );
        Map<String, Object> response = responseEntity.getBody();
        
        if (response == null || !response.containsKey("title_results")) {
            log.warn("No results found for search: {}", name);
            return new ArrayList<>();
        }

        @SuppressWarnings("unchecked")
        List<Map<String, Object>> results = (List<Map<String, Object>>) response.get("title_results");
        return results.stream().map(res -> {
            TitleDto dto = new TitleDto();
            dto.setWatchmodeId((Integer) res.get("id"));
            dto.setTitle((String) res.get("name"));
            dto.setType((String) res.get("type"));
            dto.setYear((Integer) res.get("year"));
            return dto;
        }).collect(Collectors.toList());
    }

    /**
     * Gets details for a specific title from the Watchmode API.
     * @param watchmodeId the Watchmode ID
     * @return the TitleDto details
     */
    @Override
    public TitleDto getTitleDetails(Integer watchmodeId) {
        log.info("Fetching details for watchmodeId: {}", watchmodeId);
        ResponseEntity<Map<String, Object>> responseEntity = restTemplate.exchange(
                DETAILS_URL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Map<String, Object>>() {},
                watchmodeId,
                apiKey
        );
        Map<String, Object> res = responseEntity.getBody();
        
        if (res == null) {
            log.error("Failed to fetch details for watchmodeId: {}", watchmodeId);
            return null;
        }

        TitleDto dto = new TitleDto();
        dto.setWatchmodeId((Integer) res.get("id"));
        dto.setTitle((String) res.get("title"));
        dto.setType((String) res.get("type"));
        dto.setYear((Integer) res.get("year"));
        
        @SuppressWarnings("unchecked")
        List<String> genres = (List<String>) res.get("genre_names");
        if (genres != null && !genres.isEmpty()) {
            dto.setGenre(String.join(", ", genres));
        } else {
            dto.setGenre("Unknown");
        }
        
        return dto;
    }
}
