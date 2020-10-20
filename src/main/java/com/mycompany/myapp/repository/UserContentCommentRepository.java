package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.UserContentComment;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the UserContentComment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserContentCommentRepository extends JpaRepository<UserContentComment, Long> {

}
