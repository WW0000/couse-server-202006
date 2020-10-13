package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.UserContentPraise;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the UserContentPraise entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserContentPraiseRepository extends JpaRepository<UserContentPraise, Long> {
    UserContentPraise findByContentIdAndAccountLogin(Long contentId,String login);
}
