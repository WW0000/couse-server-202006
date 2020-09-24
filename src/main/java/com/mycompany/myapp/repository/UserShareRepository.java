package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.UserShare;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the UserShare entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserShareRepository extends JpaRepository<UserShare, Long> {
}
