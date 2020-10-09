package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.ContentInfo;

import com.mycompany.myapp.domain.ContentType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the ContentInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContentInfoRepository extends JpaRepository<ContentInfo, Long> {
    /**
     * 根据类型 模糊查询：内容
     * 有分页
     * */
    Page<ContentInfo> findAllByContentInfoLikeAndContentTypeContentTypeName(Pageable pageable, String contentInfoLike, String contentType);
    //有分页 有模糊 无类型
    Page<ContentInfo> findAllByContentInfoLike(Pageable pageable, String contentInfoLike);
    //有分页 无模糊 有类型
    Page<ContentInfo> findAllByContentTypeContentTypeName(Pageable pageable,String typeName);
    //无分页 有模糊 有类型
    List<ContentInfo> findByContentInfoLikeAndContentTypeContentTypeName(String contentInfoLike,String contentType);


    /**
     * 获取当前登录账户已关注的内容列表
     * */
    @Query("select ci from ContentInfo ci,UserFans uf where uf.fansFrom.login=:login and ci.account.login=uf.fansTo.login and ci.contentInfo like :keyword")
    Page<ContentInfo> findMyFansContent(Pageable pageable, @Param("login") String login, @Param("keyword") String keyword);
    @Query("select ci from ContentInfo ci,UserFans uf where uf.fansFrom.login=:login and ci.account.login=uf.fansTo.login  ")
    Page<ContentInfo> findMyFansContent(Pageable pageable,@Param("login") String login);

    /**
     * 获取我的点赞列表
     * */
    @Query("select ucp.content from UserContentPraise ucp where ucp.account.login=:login")
    Page<ContentInfo> findMyPraise(Pageable pageable,@Param("login")String login);

    /**
     * 获取我的收藏列表
     * */
    @Query("select uf.content from UserFavorateItem uf where uf.account.login=:login")
    Page<ContentInfo> findMyFavourate(Pageable pageable,@Param("login")String login);

    /**
     * 我发布的内容列表
     * */
    Page<ContentInfo> findAllByAccount_Login(Pageable pageable,String login);

    /**
     * 删除我发布的内容
     * */
    Boolean deleteContentInfoByIdAndAccount_Login(Long id, String login);

    Page<ContentInfo> findAllByAccount_Id(Pageable pageable,Long id);

}
