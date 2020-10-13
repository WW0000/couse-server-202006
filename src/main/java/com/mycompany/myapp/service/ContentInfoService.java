package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.ContentInfo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link ContentInfo}.
 */
public interface ContentInfoService {

    /**
     * Save a contentInfo.
     *
     * @param contentInfo the entity to save.
     * @return the persisted entity.
     */
    ContentInfo save(ContentInfo contentInfo);

    /**
     * Get all the contentInfos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ContentInfo> findAll(Pageable pageable);


    /**
     * Get the "id" contentInfo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ContentInfo> findOne(Long id);

    /**
     * Delete the "id" contentInfo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
    /**
     * 根据关键字、分类查询内容列表
     * */
    Page<ContentInfo> getContent(String keyword,String typeName,Integer index,Integer size);

    Page<ContentInfo> findMyPraises(Integer index, Integer size,String login);


}
