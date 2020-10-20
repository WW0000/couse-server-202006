package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.ContentInfo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
    ContentInfo save(String login,ContentInfo contentInfo);

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
    void delete(String login,Long id) throws Exception;

    //获取分页内容
    Page<ContentInfo> getContentByTypeName(String typeName, Integer index, Integer size);

    //关键字搜索
    Page<ContentInfo> getAllContent(String keyword, String typeName, Integer index, Integer size);

    //获取当前登录账户已关注的内容列表
    Page<ContentInfo> getSubContent(String login, String keywords, Integer index, Integer size);

    //获取我的点赞列表
    Page<ContentInfo> myPraise(String login, Integer index, Integer size);

    //获取我的收藏列表
    Page<ContentInfo> myFavorate(String login, Integer index, Integer size);

    //我发布的内容列表
    Page<ContentInfo> myContent(String login, Integer index, Integer size);
}
