package com.mycompany.myapp.service.impl;


import com.mycompany.myapp.domain.ContentInfo;
import com.mycompany.myapp.domain.UserAccount;
import com.mycompany.myapp.repository.ContentInfoRepository;
import com.mycompany.myapp.repository.UserAccountRepository;
import com.mycompany.myapp.service.UserContentPraiseService;
import com.mycompany.myapp.domain.UserContentPraise;
import com.mycompany.myapp.repository.UserContentPraiseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.Optional;

/**
 * Service Implementation for managing {@link UserContentPraise}.
 */
@Service
@Transactional
public class UserContentPraiseServiceImpl implements UserContentPraiseService {

    private final Logger log = LoggerFactory.getLogger(UserContentPraiseServiceImpl.class);

    private final UserContentPraiseRepository userContentPraiseRepository;
    private final UserAccountRepository userAccountRepository;
    private ContentInfoRepository contentInfoRepository;

    public UserContentPraiseServiceImpl(UserContentPraiseRepository userContentPraiseRepository, UserAccountRepository userAccountRepository, ContentInfoRepository contentInfoRepository) {
        this.userContentPraiseRepository = userContentPraiseRepository;
        this.userAccountRepository=userAccountRepository;
        this.contentInfoRepository = contentInfoRepository;
    }

    @Override
    public UserContentPraise save(UserContentPraise userContentPraise) {
        log.debug("Request to save UserContentPraise : {}", userContentPraise);
        return userContentPraiseRepository.save(userContentPraise);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserContentPraise> findAll(Pageable pageable) {
        log.debug("Request to get all UserContentPraises");
        return userContentPraiseRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<UserContentPraise> findOne(Long id) {
        log.debug("Request to get UserContentPraise : {}", id);
        return userContentPraiseRepository.findById(id);
    }


    @Override
    public void delete(String login,Long id) throws Exception {
        log.debug("Request to delete UserContentPraise : {}", id);
        Optional<UserContentPraise> praiseOptional=this.userContentPraiseRepository.findById(id);
        if (praiseOptional.isPresent()){
            //2.判断数据对象是否是当前登录用户的
            UserContentPraise praise=praiseOptional.get();
            if (praise.getAccount()!=null
                && praise.getAccount().getLogin()!=null
                && praise.getAccount().getLogin().equals(login)){
                userContentPraiseRepository.deleteById(id);
                if (praise.getContent()!=null) {
                    ContentInfo contentInfo = praise.getContent();
                    Long praiseCount = contentInfo.getContentPraiseCount();
                    if (praiseCount == null) {
                        praiseCount = 0l;
                    } else {
                        if (praiseCount > 1) {
                            praiseCount -= 1;
                        } else {
                            praiseCount = 0l;
                        }
                    }
                    contentInfo.setContentPraiseCount(praiseCount);
                    this.contentInfoRepository.save(contentInfo);
                 }
                }else{
                    throw new Exception("id为"+id+"的数据不属于当前登录用户"+login);
                }
        }else{
            throw new Exception("未找到id为"+id+"的数据");
        }
    }

    @Override
    public UserContentPraise praise(String login, UserContentPraise praise) {
        praise.setPraiseTime(ZonedDateTime.now());
        UserAccount userAccount=this.userAccountRepository.findByLogin(login);
        if(userAccount!=null){
            praise.setAccount(userAccount);
        }
        UserContentPraise existsPraise=this.userContentPraiseRepository
            .findByContentIdAndAccountLogin(praise.getContent().getId(),login);
        //如果已经存在点赞数据，对原点赞对象的点赞时间进行更新，并存入数据库
        if(existsPraise!=null){
            existsPraise.setPraiseTime(ZonedDateTime.now());
        }else {//如果没有点赞数据，表示第一次点赞，直接保存点赞对象
            praise=this.save(praise);
        }
        return praise;
    }
}


