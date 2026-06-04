package org.vedruna.filmapi.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;
import org.vedruna.filmapi.dto.TitleDto;

@ExtendWith(MockitoExtension.class)
public class WatchmodeServiceImplTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private WatchmodeServiceImpl watchmodeService;

    private String apiKey = "test-key";

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(watchmodeService, "apiKey", apiKey);
    }

    @Test
    void searchTitles_ShouldReturnResults_WhenApiResponseIsSuccessful() {
        String name = "Inception";
        Map<String, Object> responseBody = new HashMap<>();
        Map<String, Object> result = new HashMap<>();
        result.put("id", 1);
        result.put("name", "Inception");
        result.put("type", "movie");
        result.put("year", 2010);
        result.put("image_url", "poster.jpg");
        responseBody.put("title_results", List.of(result));

        ResponseEntity<Map<String, Object>> responseEntity = ResponseEntity.ok(responseBody);
        
        when(restTemplate.exchange(
                anyString(),
                eq(HttpMethod.GET),
                isNull(),
                any(ParameterizedTypeReference.class),
                eq(apiKey),
                eq(name)
        )).thenReturn(responseEntity);

        List<TitleDto> results = watchmodeService.searchTitles(name);

        assertFalse(results.isEmpty());
        assertEquals("Inception", results.get(0).getTitle());
    }

    @Test
    void getTitleDetails_ShouldReturnDetails_WhenApiResponseIsSuccessful() {
        Integer watchmodeId = 1;
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("id", 1);
        responseBody.put("title", "Inception");
        responseBody.put("type", "movie");
        responseBody.put("year", 2010);
        responseBody.put("poster", "poster.jpg");
        responseBody.put("genre_names", List.of("Sci-Fi", "Action"));

        ResponseEntity<Map<String, Object>> responseEntity = ResponseEntity.ok(responseBody);

        when(restTemplate.exchange(
                anyString(),
                eq(HttpMethod.GET),
                isNull(),
                any(ParameterizedTypeReference.class),
                eq(watchmodeId),
                eq(apiKey)
        )).thenReturn(responseEntity);

        TitleDto result = watchmodeService.getTitleDetails(watchmodeId);

        assertNotNull(result);
        assertEquals("Inception", result.getTitle());
        assertTrue(result.getGenre().contains("Sci-Fi"));
    }

    @Test
    @SuppressWarnings("unchecked")
    void searchTitles_ShouldReturnTrending_WhenNameIsEmpty() {
        Map<String, Object> trendingResponse = new HashMap<>();
        Map<String, Object> trendingTitle = new HashMap<>();
        trendingTitle.put("id", 1);
        trendingTitle.put("title", "Trending Movie");
        trendingTitle.put("type", "movie");
        trendingTitle.put("year", 2024);
        trendingResponse.put("titles", List.of(trendingTitle));

        ResponseEntity<Map<String, Object>> trendingResponseEntity = ResponseEntity.ok(trendingResponse);

        // Mock trending call
        when(restTemplate.exchange(
                contains("list-titles"),
                eq(HttpMethod.GET),
                isNull(),
                any(ParameterizedTypeReference.class),
                eq(apiKey)
        )).thenReturn(trendingResponseEntity);

        // Mock details call (because trending titles are enriched now)
        Map<String, Object> detailsResponse = new HashMap<>();
        detailsResponse.put("id", 1);
        detailsResponse.put("title", "Trending Movie");
        detailsResponse.put("poster", "poster.jpg");

        ResponseEntity<Map<String, Object>> detailsResponseEntity = ResponseEntity.ok(detailsResponse);
        when(restTemplate.exchange(
                contains("details"),
                eq(HttpMethod.GET),
                isNull(),
                any(ParameterizedTypeReference.class),
                eq(1),
                eq(apiKey)
        )).thenReturn(detailsResponseEntity);

        List<TitleDto> results = watchmodeService.searchTitles("");

        assertEquals(1, results.size());
        assertEquals("Trending Movie", results.get(0).getTitle());
    }

    @Test
    void searchTitles_ShouldReturnEmpty_WhenApiFails() {
        when(restTemplate.exchange(anyString(), any(HttpMethod.class), any(), any(ParameterizedTypeReference.class), any(Object[].class)))
            .thenThrow(new RuntimeException("API error"));

        List<TitleDto> results = watchmodeService.searchTitles("Error");

        assertTrue(results.isEmpty());
    }
}
