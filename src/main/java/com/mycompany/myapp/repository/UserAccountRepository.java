package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.UserAccount;

import com.mycompany.myapp.domain.UserContentPraise;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the UserAccount entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
    UserAccount findByLogin(String login);
}
