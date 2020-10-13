package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.UserAccount;
import com.mycompany.myapp.repository.UserAccountRepository;
import com.mycompany.myapp.service.UserContentPraiseService;
import com.mycompany.myapp.domain.UserContentPraise;
import com.mycompany.myapp.repository.UserContentPraiseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.PublicKey;
import java.time.ZonedDateTime;
import java.util.Optional;

/**
 * Service Implementation for managing {@link UserContentPraise}.
 */
@Service
@Transactional
public class UserContentPraiseServiceImpl implements UserContentPraiseService {

    private final Logger log = LoggerFactory.getLogger(UserContentPraiseServiceImpl.class);

    private final UserContentPraiseRepository userContentPraiseRepository;
    private final UserAccountRepository userAccountRepository;

    public UserContentPraiseServiceImpl(UserContentPraiseRepository
                                            userContentPraiseRepository, UserAccountRepository userAccountRepository) {
        this.userContentPraiseRepository = userContentPraiseRepository;
        this.userAccountRepository = userAccountRepository;
    }

    @Override
    public UserContentPraise save(UserContentPraise userContentPraise) {
        log.debug("Request to save UserContentPraise : {}", userContentPraise);
        return userContentPraiseRepository.save(userContentPraise);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserContentPraise> findAll(Pageable pageable) {
        log.debug("Request to get all UserContentPraises");
        return userContentPraiseRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<UserContentPraise> findOne(Long id) {
        log.debug("Request to get UserContentPraise : {}", id);
        return userContentPraiseRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserContentPraise : {}", id);
        userContentPraiseRepository.deleteById(id);
    }

    @Override
    public UserContentPraise praise(String login, UserContentPraise praise) {
        UserAccount userAccount = this.userAccountRepository.findByLogin(login);
        if (userAccount != null) {
            praise.setAccount(userAccount);
        }
        praise.setPraiseTime(ZonedDateTime.now());

        UserContentPraise existsPraise = this.userContentPraiseRepository.
            findByContentIdAndAccountLogin(praise.getContent().getId(), login);
        if (existsPraise != null) {
            existsPraise.setPraiseTime(ZonedDateTime.now());
            praise = this.save(existsPraise);
        } else {
            praise = this.save(praise);
        }

        return praise;
    }
}
