package com.mycompany.myapp.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * 内容分类
 */
@ApiModel(description = "内容分类")
@Entity
@Table(name = "content_type")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ContentType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 分类名称
     */
    @ApiModelProperty(value = "分类名称")
    @Column(name = "content_type_name")
    private String contentTypeName;

    /**
     * 分类排序
     */
    @ApiModelProperty(value = "分类排序")
    @Column(name = "content_type_sort")
    private Integer contentTypeSort;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @Column(name = "content_type_time")
    private ZonedDateTime contentTypeTime;

    /**
     * 近期更新数量
     */
    @ApiModelProperty(value = "近期更新数量")
    @Column(name = "content_type_update_count")
    private Long contentTypeUpdateCount;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContentTypeName() {
        return contentTypeName;
    }

    public ContentType contentTypeName(String contentTypeName) {
        this.contentTypeName = contentTypeName;
        return this;
    }

    public void setContentTypeName(String contentTypeName) {
        this.contentTypeName = contentTypeName;
    }

    public Integer getContentTypeSort() {
        return contentTypeSort;
    }

    public ContentType contentTypeSort(Integer contentTypeSort) {
        this.contentTypeSort = contentTypeSort;
        return this;
    }

    public void setContentTypeSort(Integer contentTypeSort) {
        this.contentTypeSort = contentTypeSort;
    }

    public ZonedDateTime getContentTypeTime() {
        return contentTypeTime;
    }

    public ContentType contentTypeTime(ZonedDateTime contentTypeTime) {
        this.contentTypeTime = contentTypeTime;
        return this;
    }

    public void setContentTypeTime(ZonedDateTime contentTypeTime) {
        this.contentTypeTime = contentTypeTime;
    }

    public Long getContentTypeUpdateCount() {
        return contentTypeUpdateCount;
    }

    public ContentType contentTypeUpdateCount(Long contentTypeUpdateCount) {
        this.contentTypeUpdateCount = contentTypeUpdateCount;
        return this;
    }

    public void setContentTypeUpdateCount(Long contentTypeUpdateCount) {
        this.contentTypeUpdateCount = contentTypeUpdateCount;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ContentType)) {
            return false;
        }
        return id != null && id.equals(((ContentType) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ContentType{" +
            "id=" + getId() +
            ", contentTypeName='" + getContentTypeName() + "'" +
            ", contentTypeSort=" + getContentTypeSort() +
            ", contentTypeTime='" + getContentTypeTime() + "'" +
            ", contentTypeUpdateCount=" + getContentTypeUpdateCount() +
            "}";
    }
}
