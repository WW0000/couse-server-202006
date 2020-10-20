package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.UserFans;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the UserFans entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserFansRepository extends JpaRepository<UserFans, Long> {
    UserFans findByFansFromLoginAndFansToId(String login,Long toUserId);
}
