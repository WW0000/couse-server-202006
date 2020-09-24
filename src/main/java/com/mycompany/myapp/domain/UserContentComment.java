package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * 用户评论
 */
@ApiModel(description = "用户评论")
@Entity
@Table(name = "user_content_comment")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UserContentComment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 父级评论
     */
    @ApiModelProperty(value = "父级评论")
    @Column(name = "comment_pid")
    private Long commentPid;

    /**
     * 评论时间
     */
    @ApiModelProperty(value = "评论时间")
    @Column(name = "comment_time")
    private ZonedDateTime commentTime;

    /**
     * 评论内容
     */
    @ApiModelProperty(value = "评论内容")
    @Column(name = "comment_content")
    private String commentContent;

    /**
     * 客户端类型
     */
    @ApiModelProperty(value = "客户端类型")
    @Column(name = "client_type")
    private String clientType;

    /**
     * 获赞总数
     */
    @ApiModelProperty(value = "获赞总数")
    @Column(name = "praise_count")
    private Long praiseCount;

    /**
     * 用户评论
     */
    @ApiModelProperty(value = "用户评论")
    @ManyToOne
    @JsonIgnoreProperties(value = "userContentComments", allowSetters = true)
    private UserAccount account;

    /**
     * 用户评论
     */
    @ApiModelProperty(value = "用户评论")
    @ManyToOne
    @JsonIgnoreProperties(value = "userContentComments", allowSetters = true)
    private ContentInfo content;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCommentPid() {
        return commentPid;
    }

    public UserContentComment commentPid(Long commentPid) {
        this.commentPid = commentPid;
        return this;
    }

    public void setCommentPid(Long commentPid) {
        this.commentPid = commentPid;
    }

    public ZonedDateTime getCommentTime() {
        return commentTime;
    }

    public UserContentComment commentTime(ZonedDateTime commentTime) {
        this.commentTime = commentTime;
        return this;
    }

    public void setCommentTime(ZonedDateTime commentTime) {
        this.commentTime = commentTime;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public UserContentComment commentContent(String commentContent) {
        this.commentContent = commentContent;
        return this;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public String getClientType() {
        return clientType;
    }

    public UserContentComment clientType(String clientType) {
        this.clientType = clientType;
        return this;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public Long getPraiseCount() {
        return praiseCount;
    }

    public UserContentComment praiseCount(Long praiseCount) {
        this.praiseCount = praiseCount;
        return this;
    }

    public void setPraiseCount(Long praiseCount) {
        this.praiseCount = praiseCount;
    }

    public UserAccount getAccount() {
        return account;
    }

    public UserContentComment account(UserAccount userAccount) {
        this.account = userAccount;
        return this;
    }

    public void setAccount(UserAccount userAccount) {
        this.account = userAccount;
    }

    public ContentInfo getContent() {
        return content;
    }

    public UserContentComment content(ContentInfo contentInfo) {
        this.content = contentInfo;
        return this;
    }

    public void setContent(ContentInfo contentInfo) {
        this.content = contentInfo;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserContentComment)) {
            return false;
        }
        return id != null && id.equals(((UserContentComment) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserContentComment{" +
            "id=" + getId() +
            ", commentPid=" + getCommentPid() +
            ", commentTime='" + getCommentTime() + "'" +
            ", commentContent='" + getCommentContent() + "'" +
            ", clientType='" + getClientType() + "'" +
            ", praiseCount=" + getPraiseCount() +
            "}";
    }
}
