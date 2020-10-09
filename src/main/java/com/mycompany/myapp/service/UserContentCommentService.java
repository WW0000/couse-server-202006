package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.UserContentComment;

import com.mycompany.myapp.service.dto.CommentTreeItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link UserContentComment}.
 */
public interface UserContentCommentService {

    /**
     * Save a userContentComment.
     *
     * @param userContentComment the entity to save.
     * @return the persisted entity.
     */
    UserContentComment save(UserContentComment userContentComment);

    /**
     * Get all the userContentComments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<UserContentComment> findAll(Pageable pageable);


    /**
     * Get the "id" userContentComment.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UserContentComment> findOne(Long id);

    /**
     * Delete the "id" userContentComment.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
    List<CommentTreeItem> getCommentTree(Long pid,Long contentId);
}
