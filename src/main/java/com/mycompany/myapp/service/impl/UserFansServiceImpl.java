package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.UserAccount;
import com.mycompany.myapp.repository.UserAccountRepository;
import com.mycompany.myapp.service.UserFansService;
import com.mycompany.myapp.domain.UserFans;
import com.mycompany.myapp.repository.UserFansRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.Optional;

/**
 * Service Implementation for managing {@link UserFans}.
 */
@Service
@Transactional
public class UserFansServiceImpl implements UserFansService {

    private final Logger log = LoggerFactory.getLogger(UserFansServiceImpl.class);

    private final UserFansRepository userFansRepository;
    private final UserAccountRepository userAccountRepository;

    public UserFansServiceImpl(UserFansRepository userFansRepository, UserAccountRepository userAccountRepository) {
        this.userFansRepository = userFansRepository;
        this.userAccountRepository = userAccountRepository;
    }

    @Override
    public UserFans save(UserFans userFans) {
        log.debug("Request to save UserFans : {}", userFans);
        return userFansRepository.save(userFans);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserFans> findAll(Pageable pageable) {
        log.debug("Request to get all UserFans");
        return userFansRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<UserFans> findOne(Long id) {
        log.debug("Request to get UserFans : {}", id);
        return userFansRepository.findById(id);
    }

    @Override
    public void delete(String login,Long id) throws Exception {
        log.debug("Request to delete UserFans : {}", id);
        Optional<UserFans> userFansOptional = this.userFansRepository.findById(id);
        if (userFansOptional.isPresent()) {

            UserFans userfans = userFansOptional.get();
            if (userfans.getFansFrom() != null
                && userfans.getFansFrom().getLogin() != null
                && userfans.getFansFrom().getLogin().equals(login)) {
                userFansRepository.deleteById(id);
            } else {
                throw new Exception("id为" + id + "的数据不属于当前登录用户" + login);
            }
        } else {
            throw new Exception("未找到id为" + id + "的数据");
        }
    }

    @Override
    public UserFans addFans(String login, Long toUserId) throws Exception {
        //根据login查找UserAccount对象
        UserAccount user = this.userAccountRepository.findByLogin(login);
        if (user == null) {
            throw new Exception("未找到登录名为" + login + "的用户数据");
        }
        if (user.getId().equals(toUserId)) {
            throw new Exception("不需要自己关注自己");
        }
        UserFans fans=null;
        //检查当前用户是否已经关注过此用户
        UserFans existsFans = this.userFansRepository.findByFansFromLoginAndFansToId(login, toUserId);
        if (existsFans != null) {
            return existsFans;
        } else {
            fans = new UserFans();
            fans.setFansTime(ZonedDateTime.now());

            //根据toUserId查找userAccount
            Optional<UserAccount> account = this.userAccountRepository.findById(toUserId);
            UserAccount userAccount = null;
            if (account.isPresent()) {
                userAccount = account.get();
                fans.setFansFrom(user);
                fans.setFansTo(userAccount);
                fans = this.save(fans);
            } else {
                throw new Exception("编号为" + toUserId + "的用户不存在");
            }
        }
        return fans;

    }

    @Override
    public Page<UserAccount> getMyFansTo(String login, Integer index, Integer size) {
        Page<UserAccount> result=this.userAccountRepository.getMyFansTo(login, PageRequest.of(index,size));
        return result;
    }

    @Override
    public Page<UserAccount> getMyFansFrom(String login, Integer index, Integer size) {
        Page<UserAccount> result=this.userAccountRepository.getMyFansFrom(login, PageRequest.of(index,size));
        return result;
    }

}
