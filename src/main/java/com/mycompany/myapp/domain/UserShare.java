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
 * 用户分享
 */
@ApiModel(description = "用户分享")
@Entity
@Table(name = "user_share")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UserShare implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 分享时间
     */
    @ApiModelProperty(value = "分享时间")
    @Column(name = "share_time")
    private ZonedDateTime shareTime;

    /**
     * 用户分享
     */
    @ApiModelProperty(value = "用户分享")
    @ManyToOne
    @JsonIgnoreProperties(value = "userShares", allowSetters = true)
    private UserAccount account;

    /**
     * 用户分享
     */
    @ApiModelProperty(value = "用户分享")
    @ManyToOne
    @JsonIgnoreProperties(value = "userShares", allowSetters = true)
    private ContentInfo content;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getShareTime() {
        return shareTime;
    }

    public UserShare shareTime(ZonedDateTime shareTime) {
        this.shareTime = shareTime;
        return this;
    }

    public void setShareTime(ZonedDateTime shareTime) {
        this.shareTime = shareTime;
    }

    public UserAccount getAccount() {
        return account;
    }

    public UserShare account(UserAccount userAccount) {
        this.account = userAccount;
        return this;
    }

    public void setAccount(UserAccount userAccount) {
        this.account = userAccount;
    }

    public ContentInfo getContent() {
        return content;
    }

    public UserShare content(ContentInfo contentInfo) {
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
        if (!(o instanceof UserShare)) {
            return false;
        }
        return id != null && id.equals(((UserShare) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserShare{" +
            "id=" + getId() +
            ", shareTime='" + getShareTime() + "'" +
            "}";
    }
}
