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
    private static final String TRENDING_URL = "https://api.watchmode.com/v1/list-titles/?apiKey={apiKey}&limit=8&types=movie,tv_series";

    public WatchmodeServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Searches for titles by name using the Watchmode API.
     * If name is empty, returns trending titles using the list-titles endpoint.
     * 
     * @param name the title name to search for.
     * @return a list of {@link TitleDto} with basic movie/series information.
     */
    @Override
    public List<TitleDto> searchTitles(String name) {
        if (name == null || name.trim().isEmpty()) {
            log.info("Watchmode API: Fetching trending titles (empty search query)");
            return fetchTrendingTitles();
        }

        log.info("Watchmode API: Initiating search for titles with name: '{}'", name);
        try {
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
                log.warn("Watchmode API: No results found or empty response for search: '{}'", name);
                return new ArrayList<>();
            }

            @SuppressWarnings("unchecked")
            List<Map<String, Object>> results = (List<Map<String, Object>>) response.get("title_results");
            log.info("Watchmode API: Successfully found {} results for '{}'", results.size(), name);
            
            return results.stream().map(res -> {
                TitleDto dto = new TitleDto();
                Integer wmId = (Integer) res.get("id");
                dto.setId(wmId != null ? wmId.longValue() : null);
                dto.setWatchmodeId(wmId);
                dto.setTitle((String) res.get("name"));
                dto.setType((String) res.get("type"));
                dto.setYear((Integer) res.get("year"));
                dto.setPoster((String) res.get("image_url"));
                return dto;
            }).collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Watchmode API: Error occurred during search for '{}': {}", name, e.getMessage());
            return new ArrayList<>();
        }
    }

    private List<TitleDto> fetchTrendingTitles() {
        try {
            ResponseEntity<Map<String, Object>> responseEntity = restTemplate.exchange(
                    TRENDING_URL,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<Map<String, Object>>() {},
                    apiKey
            );
            Map<String, Object> response = responseEntity.getBody();
            
            if (response == null || !response.containsKey("titles")) {
                log.warn("Watchmode API: No trending titles found.");
                return new ArrayList<>();
            }

            @SuppressWarnings("unchecked")
            List<Map<String, Object>> results = (List<Map<String, Object>>) response.get("titles");
            log.info("Watchmode API: Successfully found {} trending titles. Fetching details for each...", results.size());
            
            return results.stream().map(res -> {
                Integer wmId = (Integer) res.get("id");
                if (wmId == null) return null;
                // Fetch full details to get the poster and other missing info
                return getTitleDetails(wmId);
            })
            .filter(java.util.Objects::nonNull)
            .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Watchmode API: Error occurred fetching trending titles: {}", e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Gets extended details for a specific title from the Watchmode API,
     * including synopsis, rating, year, and poster.
     * 
     * @param watchmodeId the unique Watchmode ID for the title.
     * @return the {@link TitleDto} with extended details, or null if not found.
     */
    @Override
    public TitleDto getTitleDetails(Integer watchmodeId) {
        log.info("Watchmode API: Fetching full details for watchmodeId: {}", watchmodeId);
        try {
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
                log.error("Watchmode API: Failed to fetch details for watchmodeId: {}. Null response.", watchmodeId);
                return null;
            }

            TitleDto dto = new TitleDto();
            Integer wmId = (Integer) res.get("id");
            dto.setId(wmId != null ? wmId.longValue() : null);
            dto.setWatchmodeId(wmId);
            dto.setTitle((String) res.get("title"));
            dto.setType((String) res.get("type"));
            dto.setYear((Integer) res.get("year"));
            dto.setPoster((String) res.get("poster"));
            
            @SuppressWarnings("unchecked")
            List<String> genres = (List<String>) res.get("genre_names");
            if (genres != null && !genres.isEmpty()) {
                dto.setGenre(String.join(", ", genres));
            } else {
                dto.setGenre("Unknown");
            }
            
            log.info("Watchmode API: Successfully retrieved details for '{}' (ID: {})", dto.getTitle(), watchmodeId);
            return dto;
        } catch (Exception e) {
            log.error("Watchmode API: Error fetching details for ID {}: {}", watchmodeId, e.getMessage());
            return null;
        }
    }
}
