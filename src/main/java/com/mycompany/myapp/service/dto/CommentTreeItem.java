package com.mycompany.myapp.service.dto;

import java.time.ZonedDateTime;
import java.util.List;

public class CommentTreeItem {
    private Long id;
    private Long pid;
    private ZonedDateTime commentTime;
    private String commentContent;
    private String clientType;
    private Long praiseCount;
    private Long userId;
    private Long contentId;

    private boolean leaf;
    private List<CommentTreeItem> children;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public ZonedDateTime getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(ZonedDateTime commentTime) {
        this.commentTime = commentTime;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public Long getPraiseCount() {
        return praiseCount;
    }

    public void setPraiseCount(Long praiseCount) {
        this.praiseCount = praiseCount;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getContentId() {
        return contentId;
    }

    public void setContentId(Long contentId) {
        this.contentId = contentId;
    }

    public boolean isLeaf() {
        return leaf;
    }

    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }

    public List<CommentTreeItem> getChildren() {
        return children;
    }

    public void setChildren(List<CommentTreeItem> children) {
        this.children = children;
    }
}
