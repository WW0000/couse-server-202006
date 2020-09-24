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
 * 用户点赞
 */
@ApiModel(description = "用户点赞")
@Entity
@Table(name = "user_content_praise")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UserContentPraise implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 点赞时间
     */
    @ApiModelProperty(value = "点赞时间")
    @Column(name = "praise_time")
    private ZonedDateTime praiseTime;

    /**
     * 用户点赞
     */
    @ApiModelProperty(value = "用户点赞")
    @ManyToOne
    @JsonIgnoreProperties(value = "userContentPraises", allowSetters = true)
    private UserAccount account;

    /**
     * 用户点赞
     */
    @ApiModelProperty(value = "用户点赞")
    @ManyToOne
    @JsonIgnoreProperties(value = "userContentPraises", allowSetters = true)
    private ContentInfo content;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getPraiseTime() {
        return praiseTime;
    }

    public UserContentPraise praiseTime(ZonedDateTime praiseTime) {
        this.praiseTime = praiseTime;
        return this;
    }

    public void setPraiseTime(ZonedDateTime praiseTime) {
        this.praiseTime = praiseTime;
    }

    public UserAccount getAccount() {
        return account;
    }

    public UserContentPraise account(UserAccount userAccount) {
        this.account = userAccount;
        return this;
    }

    public void setAccount(UserAccount userAccount) {
        this.account = userAccount;
    }

    public ContentInfo getContent() {
        return content;
    }

    public UserContentPraise content(ContentInfo contentInfo) {
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
        if (!(o instanceof UserContentPraise)) {
            return false;
        }
        return id != null && id.equals(((UserContentPraise) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserContentPraise{" +
            "id=" + getId() +
            ", praiseTime='" + getPraiseTime() + "'" +
            "}";
    }
}
