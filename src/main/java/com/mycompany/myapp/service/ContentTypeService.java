package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.ContentType;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link ContentType}.
 */
public interface ContentTypeService {

    /**
     * Save a contentType.
     *
     * @param contentType the entity to save.
     * @return the persisted entity.
     */
    ContentType save(ContentType contentType);

    /**
     * Get all the contentTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ContentType> findAll(Pageable pageable);


    /**
     * Get the "id" contentType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ContentType> findOne(Long id);

    /**
     * Delete the "id" contentType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
