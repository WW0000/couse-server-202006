package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.ContentInfo;

import com.mycompany.myapp.domain.ContentType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the ContentInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContentInfoRepository extends JpaRepository<ContentInfo, Long> {
    Page<ContentInfo> findByContentTypeContentTypeName(String name, Pageable page);
    Page<ContentInfo> findByContentInfoLike(String keyword, Pageable page);
    Page<ContentInfo> findByContentInfoLikeAndContentTypeContentTypeName(String keyword, String typeName, Pageable page);

    @Query(value = "select a from ContentInfo a where a.contentInfo like ?1 and ('ALL'=?2 or a.contentType.contentTypeName = ?2)")
    Page<ContentInfo> getAllContent(String keyword, String typeName, Pageable page);
    @Query(value = "select a from ContentInfo a,UserFans b where a.account.id=b.fansTo.id and b.fansFrom.login=?1 and a.contentInfo like ?2")
    Page<ContentInfo> getSubContent(String login, String keywords,Pageable pageable);
    @Query(value = "select a from ContentInfo a,UserContentPraise b where a.id=b.content.id and b.account.login=?1")
    Page<ContentInfo> getMyPraise(String login,Pageable pageable);
    @Query(value = "select a from ContentInfo a,UserFavorateItem b where a.id=b.content.id and b.account.login=?1")
    Page<ContentInfo> getMyFavorate(String login,Pageable pageable);
    Page<ContentInfo> findByAccountLogin(String login,Pageable pageable);
    Page<ContentInfo> findById(Long accountId,Pageable pageable);

}
