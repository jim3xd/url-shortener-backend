package com.assignments.shorturl.Repository;

import com.assignments.shorturl.model.UrlEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UrlRepository extends JpaRepository<UrlEntity,Long> {

    @Query(value = "Select url from UrlEntity url where url.originalUrl=?1")
    List<UrlEntity> findUrlByFullUrl(String fullUrl);
}
