package com.assignments.shorturl.controller;

import com.assignments.shorturl.exception.UrlShorterException;
import com.assignments.shorturl.model.UrlDto;
import com.assignments.shorturl.model.UrlEntity;
import com.assignments.shorturl.service.UrlService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UrlControllerTest {

    @Mock
    private UrlService mockUrlService;

    @InjectMocks
    private UrlController urlControllerUnderTest;

    @Test
    void testShortenUrl() throws Exception {
        // Setup
        String originalUrl = "http://facebook.com/";
        String shortenUrl = "http://localhost:8080/s/e";
        final UrlEntity urlEntity = new UrlEntity(null, originalUrl, null, null);

        // Configure UrlService.shortenUrl(...).
        final UrlEntity urlEntity1 = new UrlEntity(0L, originalUrl, shortenUrl, 0L);
        when(mockUrlService.shortenUrl(originalUrl)).thenReturn(urlEntity1);

        // Run the test
        final ResponseEntity<Object> result = urlControllerUnderTest.shortenUrl(urlEntity);

        // Verify the results
        assertEquals(urlEntity1,result.getBody());
    }

    @Test
    void testShortenUrl_UrlServiceThrowsUrlShorterException() throws Exception {
        // Setup
        String originalUrl = "http://facebook.com/";
        String shortenUrl = "http://localhost:8080/s/e";
        final UrlEntity urlEntity = new UrlEntity(null, originalUrl, null, null);
        when(mockUrlService.shortenUrl(originalUrl)).thenThrow(UrlShorterException.class);

        // Run the test
        assertThrows(UrlShorterException.class, () -> urlControllerUnderTest.shortenUrl(urlEntity));
    }

    @Test
    void testGetAllUrls() {
        // Setup
        String originalUrl = "http://facebook.com/";
        String shortenUrl = "http://localhost:8080/s/e";

        // Configure UrlService.getAllUrls(...).
        final UrlDto urlDto = new UrlDto(Arrays.asList(new UrlEntity(0L, originalUrl, shortenUrl, 0L)));
        when(mockUrlService.getAllUrls()).thenReturn(urlDto);

        // Run the test
        final ResponseEntity<Object> result = urlControllerUnderTest.getAllUrls();

        // Verify the results
        assertEquals(urlDto,result.getBody());

    }

    @Test
    void testRedirectToOriginalUrl() {
        // Setup
        String originalUrl = "http://facebook.com/";
        String shortenUrl = "http://localhost:8080/s/e";
        when(mockUrlService.getOriginalUrl(shortenUrl)).thenReturn(originalUrl);

        // Run the test
        final ResponseEntity<?> result = urlControllerUnderTest.redirectToOriginalUrl(shortenUrl);

        // Verify the results
        assertEquals(HttpStatus.FOUND,result.getStatusCode());

    }

    @Test
    void testRedirectToOriginalUrl_UrlServiceThrowsNoSuchElementException() {
        // Setup

        String shortenUrl = "http://localhost:8080/s/e";
        when(mockUrlService.getOriginalUrl(shortenUrl)).thenThrow(NoSuchElementException.class);

        // Run the test
        final ResponseEntity<?> result = urlControllerUnderTest.redirectToOriginalUrl(shortenUrl);

        // Verify the results
        assertEquals(HttpStatus.NOT_FOUND,result.getStatusCode());

    }
}
