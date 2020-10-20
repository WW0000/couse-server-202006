package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.ContentInfo;
import com.mycompany.myapp.domain.UserAccount;
import com.mycompany.myapp.repository.ContentInfoRepository;
import com.mycompany.myapp.repository.UserAccountRepository;
import com.mycompany.myapp.security.SecurityUtils;
import com.mycompany.myapp.service.UserFavorateItemService;
import com.mycompany.myapp.domain.UserFavorateItem;
import com.mycompany.myapp.repository.UserFavorateItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.Optional;

/**
 * Service Implementation for managing {@link UserFavorateItem}.
 */
@Service
@Transactional
public class UserFavorateItemServiceImpl implements UserFavorateItemService {

    private final Logger log = LoggerFactory.getLogger(UserFavorateItemServiceImpl.class);

    private final UserFavorateItemRepository userFavorateItemRepository;
    private final UserAccountRepository userAccountRepository;
    private final ContentInfoRepository contentInfoRepository;

    public UserFavorateItemServiceImpl(UserFavorateItemRepository userFavorateItemRepository, UserAccountRepository userAccountRepository, ContentInfoRepository contentInfoRepository) {
        this.userFavorateItemRepository = userFavorateItemRepository;
        this.userAccountRepository=userAccountRepository;
        this.contentInfoRepository = contentInfoRepository;
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
    public void delete(String login,Long id) throws Exception {
        //1.根据id找到要删除的数据对象
        log.debug("Request to delete UserFavorateItem : {}", id);
        Optional<UserFavorateItem> userFavorateItemOptional=this.userFavorateItemRepository.findById(id);
        if (userFavorateItemOptional.isPresent()){
            //2.判断数据对象是否是当前登录用户的
            UserFavorateItem item=userFavorateItemOptional.get();
            if (item.getAccount()!=null
                && item.getAccount().getLogin()!=null
                && item.getAccount().getLogin().equals(login)){
                userFavorateItemRepository.deleteById(id);
                //3.减少关联内容的收藏数
                ContentInfo contentInfo=item.getContent();
                if (contentInfo!=null){
                    Long favCount=contentInfo.getContentFavorateCount();
                    if (favCount==null){
                        favCount=0l;
                    }else{
                        if (favCount>1){
                            favCount-=1;
                        }else{
                            favCount=0l;
                        }
                    }
                }else{
                    throw new Exception("id为"+id+"的数据不属于当前登录用户"+login);
                }
            }
        }else{
            throw new Exception("未找到id为"+id+"的数据");
        }
    }

    @Override
    public UserFavorateItem favorate(String login, Long contentId) throws Exception {
        //根据login查找UserAccount对象
        UserAccount user = this.userAccountRepository.findByLogin(login);
        if (user == null) {
            throw new Exception("未找到登录名为" + login + "的用户数据");
        }
        UserFavorateItem userFavorateItem = null;
        //检查当前用户是否已经收藏过此内容
        UserFavorateItem existsItem = this.userFavorateItemRepository.findByContentIdAndAccountLogin(contentId, login);
        //如果已经收藏过
        if (existsItem != null) {
            return existsItem;
        } else {//添加新数据
            userFavorateItem = new UserFavorateItem();
            userFavorateItem.setFavorateTime(ZonedDateTime.now());
            userFavorateItem.setAccount(user);
            //根据ContentId,查找ContentInfo对象
            Optional<ContentInfo> contentInfoOptional = this.contentInfoRepository.findById(contentId);
            if (contentInfoOptional.isPresent()) {
                ContentInfo contentInfo = contentInfoOptional.get();
                userFavorateItem.setContent(contentInfo);
                userFavorateItem = this.userFavorateItemRepository.save(userFavorateItem);
                Long favoriteCount = contentInfo.getContentFavorateCount();
                if (favoriteCount == null) {
                    favoriteCount = 1l;
                } else {
                    favoriteCount += 1;
                }
                contentInfo.setContentFavorateCount(favoriteCount);
                contentInfo = this.contentInfoRepository.save(contentInfo);
                return userFavorateItem;
            } else {
                throw new Exception("编号为" + contentId + "的内容不存在");
            }

        }

    }

}
