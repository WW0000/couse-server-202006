package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.UserFavorateItem;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link UserFavorateItem}.
 */
public interface UserFavorateItemService {

    /**
     * Save a userFavorateItem.
     *
     * @param userFavorateItem the entity to save.
     * @return the persisted entity.
     */
    UserFavorateItem save(UserFavorateItem userFavorateItem);

    /**
     * Get all the userFavorateItems.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<UserFavorateItem> findAll(Pageable pageable);


    /**
     * Get the "id" userFavorateItem.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UserFavorateItem> findOne(Long id);

    /**
     * Delete the "id" userFavorateItem.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
