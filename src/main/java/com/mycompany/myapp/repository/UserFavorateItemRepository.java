package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.UserFavorateItem;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the UserFavorateItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserFavorateItemRepository extends JpaRepository<UserFavorateItem, Long> {
}
