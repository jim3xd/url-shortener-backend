package com.assignments.shorturl.service;

import com.assignments.shorturl.exception.UrlShorterException;
import com.assignments.shorturl.model.UrlDto;
import com.assignments.shorturl.model.UrlEntity;

import java.util.List;

public interface UrlService {

    /**
     * Performs Validation check to decide id the Url is valid or not
     *
     * @param originalUrl
     * @throws UrlShorterException
     */
    void validate(String originalUrl) throws UrlShorterException;

    /**
     * Shortens a valid Url
     *
     * @param originalUrl
     * @return UrlEntity which contains the Shortened Url
     * @throws UrlShorterException
     */
    UrlEntity shortenUrl(String originalUrl) throws UrlShorterException;


    /**
     *
     * @return List of all shortened Urls stored in the DB with details like
     * number of times it was clicked
     */
    UrlDto getAllUrls();

    /**
     *
     * @param shortString
     * @return Original Url used to generate the short Url
     */
    String getOriginalUrl(String shortString);
}
