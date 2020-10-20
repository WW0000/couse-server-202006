package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.UserAccount;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the UserAccount entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
    UserAccount findByLogin(String login);

    @Query("select a from UserAccount a,UserFans b" +
        " where a.id=b.fansTo.id and b.fansFrom.login=?1")
    Page<UserAccount> getMyFansTo(String login, Pageable pageable);

    @Query("select a from UserAccount a,UserFans b" +
        " where a.id=b.fansFrom.id and b.fansTo.login=?1")
    Page<UserAccount> getMyFansFrom(String login, Pageable pageable);
}
