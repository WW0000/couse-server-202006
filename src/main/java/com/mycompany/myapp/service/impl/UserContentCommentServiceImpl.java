package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.UserContentCommentService;
import com.mycompany.myapp.domain.UserContentComment;
import com.mycompany.myapp.repository.UserContentCommentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link UserContentComment}.
 */
@Service
@Transactional
public class UserContentCommentServiceImpl implements UserContentCommentService {

    private final Logger log = LoggerFactory.getLogger(UserContentCommentServiceImpl.class);

    private final UserContentCommentRepository userContentCommentRepository;

    public UserContentCommentServiceImpl(UserContentCommentRepository userContentCommentRepository) {
        this.userContentCommentRepository = userContentCommentRepository;
    }

    @Override
    public UserContentComment save(UserContentComment userContentComment) {
        log.debug("Request to save UserContentComment : {}", userContentComment);
        return userContentCommentRepository.save(userContentComment);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserContentComment> findAll(Pageable pageable) {
        log.debug("Request to get all UserContentComments");
        return userContentCommentRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<UserContentComment> findOne(Long id) {
        log.debug("Request to get UserContentComment : {}", id);
        return userContentCommentRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserContentComment : {}", id);
        userContentCommentRepository.deleteById(id);
    }
}
