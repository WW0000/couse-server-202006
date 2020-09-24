package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.ContentTypeService;
import com.mycompany.myapp.domain.ContentType;
import com.mycompany.myapp.repository.ContentTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ContentType}.
 */
@Service
@Transactional
public class ContentTypeServiceImpl implements ContentTypeService {

    private final Logger log = LoggerFactory.getLogger(ContentTypeServiceImpl.class);

    private final ContentTypeRepository contentTypeRepository;

    public ContentTypeServiceImpl(ContentTypeRepository contentTypeRepository) {
        this.contentTypeRepository = contentTypeRepository;
    }

    @Override
    public ContentType save(ContentType contentType) {
        log.debug("Request to save ContentType : {}", contentType);
        return contentTypeRepository.save(contentType);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ContentType> findAll(Pageable pageable) {
        log.debug("Request to get all ContentTypes");
        return contentTypeRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ContentType> findOne(Long id) {
        log.debug("Request to get ContentType : {}", id);
        return contentTypeRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ContentType : {}", id);
        contentTypeRepository.deleteById(id);
    }
}
