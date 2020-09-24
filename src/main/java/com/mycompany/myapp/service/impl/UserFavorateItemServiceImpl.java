package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.UserFavorateItemService;
import com.mycompany.myapp.domain.UserFavorateItem;
import com.mycompany.myapp.repository.UserFavorateItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link UserFavorateItem}.
 */
@Service
@Transactional
public class UserFavorateItemServiceImpl implements UserFavorateItemService {

    private final Logger log = LoggerFactory.getLogger(UserFavorateItemServiceImpl.class);

    private final UserFavorateItemRepository userFavorateItemRepository;

    public UserFavorateItemServiceImpl(UserFavorateItemRepository userFavorateItemRepository) {
        this.userFavorateItemRepository = userFavorateItemRepository;
    }

    @Override
    public UserFavorateItem save(UserFavorateItem userFavorateItem) {
        log.debug("Request to save UserFavorateItem : {}", userFavorateItem);
        return userFavorateItemRepository.save(userFavorateItem);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserFavorateItem> findAll(Pageable pageable) {
        log.debug("Request to get all UserFavorateItems");
        return userFavorateItemRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<UserFavorateItem> findOne(Long id) {
        log.debug("Request to get UserFavorateItem : {}", id);
        return userFavorateItemRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserFavorateItem : {}", id);
        userFavorateItemRepository.deleteById(id);
    }
}
