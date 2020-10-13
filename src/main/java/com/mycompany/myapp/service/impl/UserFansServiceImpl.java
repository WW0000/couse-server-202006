package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.UserAccount;
import com.mycompany.myapp.domain.UserFavorateItem;
import com.mycompany.myapp.repository.UserAccountRepository;
import com.mycompany.myapp.service.UserFansService;
import com.mycompany.myapp.domain.UserFans;
import com.mycompany.myapp.repository.UserFansRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link UserFans}.
 */
@Service
@Transactional
public class UserFansServiceImpl implements UserFansService {

    private final Logger log = LoggerFactory.getLogger(UserFansServiceImpl.class);

    private final UserFansRepository userFansRepository;

    public UserFansServiceImpl(UserFansRepository userFansRepository) {
        this.userFansRepository = userFansRepository;
    }

    @Override
    public UserFans save(UserFans userFans) {
        log.debug("Request to save UserFans : {}", userFans);
        return userFansRepository.save(userFans);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserFans> findAll(Pageable pageable) {
        log.debug("Request to get all UserFans");
        return userFansRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<UserFans> findOne(Long id) {
        log.debug("Request to get UserFans : {}", id);
        return userFansRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserFans : {}", id);
        userFansRepository.deleteById(id);
    }
}
