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
 * 用户收藏
 */
@ApiModel(description = "用户收藏")
@Entity
@Table(name = "user_favorate_item")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UserFavorateItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 收藏时间
     */
    @ApiModelProperty(value = "收藏时间")
    @Column(name = "favorate_time")
    private ZonedDateTime favorateTime;

    /**
     * 收藏——用户
     */
    @ApiModelProperty(value = "收藏——用户")
    @ManyToOne
    @JsonIgnoreProperties(value = "userFavorateItems", allowSetters = true)
    private UserAccount account;

    /**
     * 收藏——内容（视频文章）
     */
    @ApiModelProperty(value = "收藏——内容（视频文章）")
    @ManyToOne
    @JsonIgnoreProperties(value = "userFavorateItems", allowSetters = true)
    private ContentInfo content;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getFavorateTime() {
        return favorateTime;
    }

    public UserFavorateItem favorateTime(ZonedDateTime favorateTime) {
        this.favorateTime = favorateTime;
        return this;
    }

    public void setFavorateTime(ZonedDateTime favorateTime) {
        this.favorateTime = favorateTime;
    }

    public UserAccount getAccount() {
        return account;
    }

    public UserFavorateItem account(UserAccount userAccount) {
        this.account = userAccount;
        return this;
    }

    public void setAccount(UserAccount userAccount) {
        this.account = userAccount;
    }

    public ContentInfo getContent() {
        return content;
    }

    public UserFavorateItem content(ContentInfo contentInfo) {
        this.content = contentInfo;
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserFavorateItem)) {
            return false;
        }
        return id != null && id.equals(((UserFavorateItem) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserFavorateItem{" +
            "id=" + getId() +
            ", favorateTime='" + getFavorateTime() + "'" +
            "}";
    }

    public void setContent(ContentInfo contentInfo) {
    }
}
