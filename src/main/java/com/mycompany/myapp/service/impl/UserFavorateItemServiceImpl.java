package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.ContentInfo;
import com.mycompany.myapp.domain.UserAccount;
import com.mycompany.myapp.repository.ContentInfoRepository;
import com.mycompany.myapp.repository.UserAccountRepository;
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
    private final UserAccountRepository UserAccountRepository;
    private final ContentInfoRepository ContentInfoRepository;

    public UserFavorateItemServiceImpl(UserFavorateItemRepository userFavorateItemRepository, com.mycompany.myapp.repository.UserAccountRepository userAccountRepository, com.mycompany.myapp.repository.ContentInfoRepository contentInfoRepository) {
        this.userFavorateItemRepository = userFavorateItemRepository;
        this.UserAccountRepository = userAccountRepository;
        this.ContentInfoRepository = contentInfoRepository;
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

    @Override
    public UserFavorateItem favorite(String login, Long contentId) throws Exception {
        //根据login查找UserAccount对象
        UserAccount user = this.UserAccountRepository.findByLogin(login);
        if (user == null) {
            throw new Exception("未找到登录名为" + login + "的用户数据");
        }
        UserFavorateItem userFavorateItem = null;
        //检查当前用户是否已经收藏过此类文件
        UserFavorateItem existsItem = this.userFavorateItemRepository
            .findByContentIdAndAccountLogin(contentId, login);
        //如果已经收藏过
        if (existsItem != null) {
            return existsItem;
        } else {//添加新数据
            userFavorateItem = new UserFavorateItem();
            userFavorateItem.setFavorateTime(ZonedDateTime.now());
            userFavorateItem.setAccount(user);
            //根据ContentId,查询ContentInfo对象
            Optional<ContentInfo> contentInfoOptional = this.ContentInfoRepository.findById(contentId);
            if (contentInfoOptional.isPresent()) {
                ContentInfo contentInfo = contentInfoOptional.get();
                userFavorateItem.setContent(contentInfo);
                userFavorateItem = this.userFavorateItemRepository.save(userFavorateItem);
                //在保存后，更新contentInfo的收藏数量
                Long favoriteCount=contentInfo.getContentFavorateCount();
                if(favoriteCount==null){
                    favoriteCount=1l;
                }else {
                    favoriteCount+=1;
                }
                contentInfo.setContentFavorateCount(favoriteCount);
                ContentInfoRepository.save(contentInfo);
                return userFavorateItem;
            }else
            {
                throw new Exception("编号为"+contentId+"的内容不存在");
            }
        }
    }
}
