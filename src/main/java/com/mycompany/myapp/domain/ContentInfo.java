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
 * 内容（视频文章）
 */
@ApiModel(description = "内容（视频文章）")
@Entity
@Table(name = "content_info")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ContentInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 发布者
     */
    @ApiModelProperty(value = "发布者")
    @Column(name = "content_actor")
    private String contentActor;

    /**
     * 内容信息
     */
    @ApiModelProperty(value = "内容信息")
    @Column(name = "content_info")
    private String contentInfo;

    /**
     * 内容图片
     */
    @ApiModelProperty(value = "内容图片")
    @Column(name = "content_img")
    private String contentImg;

    /**
     * 发布时间
     */
    @ApiModelProperty(value = "发布时间")
    @Column(name = "content_time")
    private ZonedDateTime contentTime;

    /**
     * 点赞次数
     */
    @ApiModelProperty(value = "点赞次数")
    @Column(name = "content_praise_count")
    private Long contentPraiseCount;

    /**
     * 收藏总数
     */
    @ApiModelProperty(value = "收藏总数")
    @Column(name = "content_favorate_count")
    private Long contentFavorateCount;

    /**
     * 评论数
     */
    @ApiModelProperty(value = "评论数")
    @Column(name = "content_comment_count")
    private Long contentCommentCount;

    /**
     * 图片标签
     */
    @ApiModelProperty(value = "图片标签")
    @Column(name = "content_img_label")
    private String contentImgLabel;

    /**
     * 内容（视频文章）
     */
    @ApiModelProperty(value = "内容（视频文章）")
    @ManyToOne
    @JsonIgnoreProperties(value = "contentInfos", allowSetters = true)
    private UserAccount account;

    /**
     * 内容（视频文章）——分类
     */
    @ApiModelProperty(value = "内容（视频文章）——分类")
    @ManyToOne
    @JsonIgnoreProperties(value = "contentInfos", allowSetters = true)
    private ContentType contentType;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContentActor() {
        return contentActor;
    }

    public ContentInfo contentActor(String contentActor) {
        this.contentActor = contentActor;
        return this;
    }

    public void setContentActor(String contentActor) {
        this.contentActor = contentActor;
    }

    public String getContentInfo() {
        return contentInfo;
    }

    public ContentInfo contentInfo(String contentInfo) {
        this.contentInfo = contentInfo;
        return this;
    }

    public void setContentInfo(String contentInfo) {
        this.contentInfo = contentInfo;
    }

    public String getContentImg() {
        return contentImg;
    }

    public ContentInfo contentImg(String contentImg) {
        this.contentImg = contentImg;
        return this;
    }

    public void setContentImg(String contentImg) {
        this.contentImg = contentImg;
    }

    public ZonedDateTime getContentTime() {
        return contentTime;
    }

    public ContentInfo contentTime(ZonedDateTime contentTime) {
        this.contentTime = contentTime;
        return this;
    }

    public void setContentTime(ZonedDateTime contentTime) {
        this.contentTime = contentTime;
    }

    public Long getContentPraiseCount() {
        return contentPraiseCount;
    }

    public ContentInfo contentPraiseCount(Long contentPraiseCount) {
        this.contentPraiseCount = contentPraiseCount;
        return this;
    }

    public void setContentPraiseCount(Long contentPraiseCount) {
        this.contentPraiseCount = contentPraiseCount;
    }

    public Long getContentFavorateCount() {
        return contentFavorateCount;
    }

    public ContentInfo contentFavorateCount(Long contentFavorateCount) {
        this.contentFavorateCount = contentFavorateCount;
        return this;
    }

    public void setContentFavorateCount(Long contentFavorateCount) {
        this.contentFavorateCount = contentFavorateCount;
    }

    public Long getContentCommentCount() {
        return contentCommentCount;
    }

    public ContentInfo contentCommentCount(Long contentCommentCount) {
        this.contentCommentCount = contentCommentCount;
        return this;
    }

    public void setContentCommentCount(Long contentCommentCount) {
        this.contentCommentCount = contentCommentCount;
    }

    public String getContentImgLabel() {
        return contentImgLabel;
    }

    public ContentInfo contentImgLabel(String contentImgLabel) {
        this.contentImgLabel = contentImgLabel;
        return this;
    }

    public void setContentImgLabel(String contentImgLabel) {
        this.contentImgLabel = contentImgLabel;
    }

    public UserAccount getAccount() {
        return account;
    }

    public ContentInfo account(UserAccount userAccount) {
        this.account = userAccount;
        return this;
    }

    public void setAccount(UserAccount userAccount) {
        this.account = userAccount;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public ContentInfo contentType(ContentType contentType) {
        this.contentType = contentType;
        return this;
    }

    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ContentInfo)) {
            return false;
        }
        return id != null && id.equals(((ContentInfo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ContentInfo{" +
            "id=" + getId() +
            ", contentActor='" + getContentActor() + "'" +
            ", contentInfo='" + getContentInfo() + "'" +
            ", contentImg='" + getContentImg() + "'" +
            ", contentTime='" + getContentTime() + "'" +
            ", contentPraiseCount=" + getContentPraiseCount() +
            ", contentFavorateCount=" + getContentFavorateCount() +
            ", contentCommentCount=" + getContentCommentCount() +
            ", contentImgLabel='" + getContentImgLabel() + "'" +
            "}";
    }
}
