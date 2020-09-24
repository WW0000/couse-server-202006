package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.ContentInfoService;
import com.mycompany.myapp.domain.ContentInfo;
import com.mycompany.myapp.repository.ContentInfoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ContentInfo}.
 */
@Service
@Transactional
public class ContentInfoServiceImpl implements ContentInfoService {

    private final Logger log = LoggerFactory.getLogger(ContentInfoServiceImpl.class);

    private final ContentInfoRepository contentInfoRepository;

    public ContentInfoServiceImpl(ContentInfoRepository contentInfoRepository) {
        this.contentInfoRepository = contentInfoRepository;
    }

    @Override
    public ContentInfo save(ContentInfo contentInfo) {
        log.debug("Request to save ContentInfo : {}", contentInfo);
        return contentInfoRepository.save(contentInfo);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ContentInfo> findAll(Pageable pageable) {
        log.debug("Request to get all ContentInfos");
        return contentInfoRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ContentInfo> findOne(Long id) {
        log.debug("Request to get ContentInfo : {}", id);
        return contentInfoRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ContentInfo : {}", id);
        contentInfoRepository.deleteById(id);
    }
}
