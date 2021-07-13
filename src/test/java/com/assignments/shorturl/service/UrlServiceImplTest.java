package com.assignments.shorturl.service;

import com.assignments.shorturl.Repository.UrlRepository;
import com.assignments.shorturl.exception.UrlShorterException;
import com.assignments.shorturl.model.UrlDto;
import com.assignments.shorturl.model.UrlEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UrlServiceImplTest {

    @Mock
    private UrlRepository mockUrlRepository;

    @InjectMocks
    private UrlServiceImpl urlServiceImplUnderTest;

    @Test
    void testValidate() throws Exception {
        // Setup

        // Run the test

        urlServiceImplUnderTest.validate("http://facebook.com/");
        // Verify the results
    }

    @Test
    void testValidate_ThrowsUrlShorterException() {
        // Setup

        // Run the test
        assertThrows(UrlShorterException.class, () -> urlServiceImplUnderTest.validate("originalUrl"));
    }

    @Test
    void testShortenUrl() throws Exception {
        // Setup
        String originalUrl = "http://facebook.com/";

        // Configure UrlRepository.findUrlByFullUrl(...).
        List<UrlEntity> urlEntities = new ArrayList<>();
        urlEntities.add(new UrlEntity(0L, originalUrl, "http://localhost:8080/s/e", 0L));
        when(mockUrlRepository.findUrlByFullUrl(originalUrl)).thenReturn(urlEntities);

        // Run the test
        final UrlEntity result = urlServiceImplUnderTest.shortenUrl(originalUrl);

        // Verify the results
        assertEquals(0, result.getId());
    }

    @Test
    void testShortenUrl_UrlRepositoryFindUrlByFullUrlReturnsNoItems() throws Exception {
        // Setup
        String originalUrl = "http://facebook.com/";

        // Configure UrlRepository.findUrlByFullUrl(...).
        when(mockUrlRepository.findUrlByFullUrl(originalUrl)).thenReturn(Collections.emptyList());


        // Configure UrlRepository.save(...).
        final UrlEntity urlEntityResult = new UrlEntity(0L, originalUrl, "http://localhost:8080/s/e", 0L);
        when(mockUrlRepository.save((UrlEntity) any())).thenReturn(urlEntityResult);

        // Run the test
        final UrlEntity result = urlServiceImplUnderTest.shortenUrl(originalUrl);

        // Verify the results
        assertEquals(0, result.getId());
    }



    @Test
    void testGetAllUrls() {
        // Setup
        final UrlDto expectedResult = new UrlDto(Arrays.asList(new UrlEntity(0L, "originalUrl", "shortUrl", 0L)));

        // Configure UrlRepository.findAll(...).
        final List<UrlEntity> urlEntities = Arrays.asList(new UrlEntity(0L, "originalUrl", "shortUrl", 0L));
        when(mockUrlRepository.findAll()).thenReturn(urlEntities);

        // Run the test
        final UrlDto result = urlServiceImplUnderTest.getAllUrls();

        // Verify the results
        assertEquals(expectedResult, result);
    }


    @Test
    void testGetOriginalUrl() {
        // Setup
        String originalUrl = "http://facebook.com/";
        String shortenUrl = "http://localhost:8080/s/e";

        // Configure UrlRepository.findById(...).
        final Optional<UrlEntity> urlEntity = Optional.of(new UrlEntity(0L, originalUrl, shortenUrl, 0L));
        when(mockUrlRepository.findById((Long) any())).thenReturn(urlEntity);

        // Configure UrlRepository.save(...).
        final UrlEntity urlEntity1 = new UrlEntity(0L, originalUrl, shortenUrl, 1L);
        when(mockUrlRepository.save((UrlEntity) any())).thenReturn(urlEntity1);

        // Run the test
        final String result = urlServiceImplUnderTest.getOriginalUrl(shortenUrl);

        // Verify the results
        assertEquals(originalUrl, result);
    }

    @Test
    void testGetOriginalUrl_UrlRepositoryFindByIdReturnsAbsent() {

        // Setup
        String originalUrl = "http://facebook.com/";
        String shortenUrl = "http://localhost:8080/s/e";

        // Configure UrlRepository.findById(...).
        when(mockUrlRepository.findById((Long) any())).thenReturn(Optional.empty());


        // Verify the results
        assertThrows(NoSuchElementException.class, () -> urlServiceImplUnderTest.getOriginalUrl(shortenUrl));




    }
}
