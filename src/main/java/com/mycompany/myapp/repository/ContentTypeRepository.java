package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.ContentType;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the ContentType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContentTypeRepository extends JpaRepository<ContentType, Long> {
    List<ContentType> findByOrderByContentTypeSort();
}
