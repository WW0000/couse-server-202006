package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.UserAccount;
import com.mycompany.myapp.domain.UserFans;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link UserFans}.
 */
public interface UserFansService {

    /**
     * Save a userFans.
     *
     * @param userFans the entity to save.
     * @return the persisted entity.
     */
    UserFans save(UserFans userFans);

    /**
     * Get all the userFans.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<UserFans> findAll(Pageable pageable);


    /**
     * Get the "id" userFans.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UserFans> findOne(Long id);

    /**
     * Delete the "id" userFans.
     *
     * @param id the id of the entity.
     */
    void delete(String login,Long id) throws  Exception;

    UserFans addFans(String login,Long toUserId) throws Exception;

    Page<UserAccount> getMyFansTo(String login, Integer index, Integer size);
    Page<UserAccount> getMyFansFrom(String login, Integer index, Integer size);
}
