package com.assignments.shorturl.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class UrlEntity {
    /**
     * Specifies Primary Key ID UrlEntity
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(hidden = true)
    private Long id;

    /**
     * Specifies Original Url
     */
    @Column(name = "original_url")
    private String originalUrl;


    /**
     * Specifies the shorten Url
     */
    @ApiModelProperty(hidden = true)
    @Column(name = "short_url")
    private String shortUrl;


    /**
     * Specifies the number of times anyone opened the short Url
     */
    @ApiModelProperty(hidden = true)
    @Column(name = "count")
    private Long usageCount = 0l;

    public UrlEntity(String originalUrl) {
        this.originalUrl = originalUrl;
    }

}
