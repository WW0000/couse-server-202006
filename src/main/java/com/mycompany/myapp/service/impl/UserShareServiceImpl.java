package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.UserShareService;
import com.mycompany.myapp.domain.UserShare;
import com.mycompany.myapp.repository.UserShareRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link UserShare}.
 */
@Service
@Transactional
public class UserShareServiceImpl implements UserShareService {

    private final Logger log = LoggerFactory.getLogger(UserShareServiceImpl.class);

    private final UserShareRepository userShareRepository;

    public UserShareServiceImpl(UserShareRepository userShareRepository) {
        this.userShareRepository = userShareRepository;
    }

    @Override
    public UserShare save(UserShare userShare) {
        log.debug("Request to save UserShare : {}", userShare);
        return userShareRepository.save(userShare);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserShare> findAll(Pageable pageable) {
        log.debug("Request to get all UserShares");
        return userShareRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<UserShare> findOne(Long id) {
        log.debug("Request to get UserShare : {}", id);
        return userShareRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserShare : {}", id);
        userShareRepository.deleteById(id);
    }
}
