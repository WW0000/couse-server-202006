package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.UserShare;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link UserShare}.
 */
public interface UserShareService {

    /**
     * Save a userShare.
     *
     * @param userShare the entity to save.
     * @return the persisted entity.
     */
    UserShare save(UserShare userShare);

    /**
     * Get all the userShares.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<UserShare> findAll(Pageable pageable);


    /**
     * Get the "id" userShare.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UserShare> findOne(Long id);

    /**
     * Delete the "id" userShare.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
