package com.mycompany.myapp.service.impl;

import com.alibaba.fastjson.util.TypeUtils;
import com.mycompany.myapp.service.UserContentCommentService;
import com.mycompany.myapp.domain.UserContentComment;
import com.mycompany.myapp.repository.UserContentCommentRepository;
import com.mycompany.myapp.service.dto.CommentTreeItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Service Implementation for managing {@link UserContentComment}.
 */
@Service
@Transactional
public class UserContentCommentServiceImpl implements UserContentCommentService {

    private final Logger log = LoggerFactory.getLogger(UserContentCommentServiceImpl.class);

    private final UserContentCommentRepository userContentCommentRepository;
    private  JdbcTemplate jdbcTemplate ;

    public UserContentCommentServiceImpl(UserContentCommentRepository userContentCommentRepository,JdbcTemplate jdbcTemplate) {
        this.userContentCommentRepository = userContentCommentRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public UserContentComment save(UserContentComment userContentComment) {
        log.debug("Request to save UserContentComment : {}", userContentComment);
        return userContentCommentRepository.save(userContentComment);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserContentComment> findAll(Pageable pageable) {
        log.debug("Request to get all UserContentComments");
        return userContentCommentRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<UserContentComment> findOne(Long id) {
        log.debug("Request to get UserContentComment : {}", id);
        return userContentCommentRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserContentComment : {}", id);
        userContentCommentRepository.deleteById(id);
    }

    @Override
    public List<CommentTreeItem> getCommentTree(Long pid,Long contentId) {
        String sql="select id,comment_pid,comment_content, "+
            "       (select count(1) from user_content_comment b where comment_pid=a.id and b.content_id="+contentId+") as leaf "+
            "from user_content_comment a where a.comment_pid="+pid+" and a.content_id="+contentId;
        List<Map<String,Object>> sqlResult=this.jdbcTemplate.queryForList(sql);

        List<CommentTreeItem> result=new ArrayList<CommentTreeItem>();

        if(sqlResult!=null&&!sqlResult.isEmpty()){
            for(Map<String,Object> sqlItem:sqlResult){
                CommentTreeItem treeItem=new CommentTreeItem();
                treeItem.setId(TypeUtils.castToLong(sqlItem.get("id")));
                treeItem.setPid(TypeUtils.castToLong(sqlItem.get("comment_pid")));
                treeItem.setCommentContent(TypeUtils.castToString(sqlItem.get("comment_content")));
                Integer leafCount=TypeUtils.castToInt(sqlItem.get("leaf"));
                if(leafCount>0){
                    treeItem.setLeaf(false);
                }else{
                    treeItem.setLeaf(true);
                }
                if(!treeItem.isLeaf()){
                    treeItem.setChildren(getCommentTree(treeItem.getId(),contentId));
                }
                result.add(treeItem);
            }
        }
        return result;
    }


}
