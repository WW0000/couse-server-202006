package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.UserContentPraise;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link UserContentPraise}.
 */
public interface UserContentPraiseService {

    /**
     * Save a userContentPraise.
     *
     * @param userContentPraise the entity to save.
     * @return the persisted entity.
     */
    UserContentPraise save(UserContentPraise userContentPraise);

    /**
     * Get all the userContentPraises.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<UserContentPraise> findAll(Pageable pageable);


    /**
     * Get the "id" userContentPraise.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UserContentPraise> findOne(Long id);

    /**
     * Delete the "id" userContentPraise.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
