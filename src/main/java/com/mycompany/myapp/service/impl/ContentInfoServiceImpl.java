package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.UserAccount;
import com.mycompany.myapp.repository.UserAccountRepository;
import com.mycompany.myapp.service.ContentInfoService;
import com.mycompany.myapp.domain.ContentInfo;
import com.mycompany.myapp.repository.ContentInfoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.Optional;

/**
 * Service Implementation for managing {@link ContentInfo}.
 */
@Service
@Transactional
public class ContentInfoServiceImpl implements ContentInfoService {

    private final Logger log = LoggerFactory.getLogger(ContentInfoServiceImpl.class);

    private final ContentInfoRepository contentInfoRepository;

    private final JdbcTemplate jdbcTemplate;
    private UserAccountRepository userAccountRepository;

    public ContentInfoServiceImpl(ContentInfoRepository contentInfoRepository, JdbcTemplate jdbcTemplate, UserAccountRepository userAccountRepository) {
        this.contentInfoRepository = contentInfoRepository;
        this.jdbcTemplate=jdbcTemplate;
        this.userAccountRepository = userAccountRepository;
    }

    @Override
    public ContentInfo save(String login,ContentInfo contentInfo) {
        log.debug("Request to save ContentInfo : {}", contentInfo);
        //新增状态
        if (contentInfo.getId()==null){
            //发布时间
            contentInfo.setContentTime(ZonedDateTime.now());
            //点赞数、评论数、收藏数初始化为0
            contentInfo.setContentPraiseCount(0l);
            contentInfo.setContentCommentCount(0l);
            contentInfo.setContentFavorateCount(0l);
            UserAccount account=this.userAccountRepository.findByLogin(login);
            contentInfo.setAccount(account);
        }

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
    public void delete(String login,Long id) throws Exception {
        log.debug("Request to delete ContentInfo : {}", id);
        Optional<ContentInfo> contentInfoOptional=this.contentInfoRepository.findById(id);
        if(contentInfoOptional.isPresent()){
            //是否当前登陆的用户，可以删除
            ContentInfo contentInfo=contentInfoOptional.get();
            if (contentInfo.getAccount()!=null
                && contentInfo.getAccount().getLogin()!=null
                && contentInfo.getAccount().getLogin().equals(login)){
                contentInfoRepository.deleteById(id);

            }else{
                throw new Exception("id为"+id+"的数据不属于当前登录用户"+login);
            }
        }else{
            throw new Exception("未找到id为"+id+"的数据");
        }

    }


    @Override
    public Page<ContentInfo> getAllContent(String keyword, String typeName, Integer index, Integer size) {
        keyword = "%" + keyword + "%";
        Page<ContentInfo> result = null;
        result = this.contentInfoRepository.getAllContent(keyword,typeName,PageRequest.of(index,size));
        return result;
    }

    public Page<ContentInfo> getContentByTypeName(String typeName,Integer index,Integer size) {

        Page<ContentInfo> result = this.contentInfoRepository
            .findByContentTypeContentTypeName(typeName, PageRequest.of(index,size));
        return result;
    }
    @Override
    public Page<ContentInfo> getSubContent(String login, String keywords, Integer index, Integer size) {
        keywords="%"+keywords+"%";
        Page<ContentInfo> result=this.contentInfoRepository.getSubContent(login,keywords,PageRequest.of(index,size));
        return result;
    }
    @Override
    public Page<ContentInfo> myPraise(String login, Integer index, Integer size) {

        Page<ContentInfo> result=this.contentInfoRepository.getMyPraise(login,PageRequest.of(index,size));
        return result;
    }

    @Override
    public Page<ContentInfo> myFavorate(String login, Integer index, Integer size) {

        Page<ContentInfo> result=this.contentInfoRepository.getMyFavorate(login,PageRequest.of(index,size));
        return result;
    }

    @Override
    public Page<ContentInfo> myContent(String login, Integer index, Integer size) {

        Page<ContentInfo> result=this.contentInfoRepository.findByAccountLogin(login,PageRequest.of(index,size));
        return result;
    }
}



