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
 * 用户关注
 */
@ApiModel(description = "用户关注")
@Entity
@Table(name = "user_fans")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UserFans implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 关注时间
     */
    @ApiModelProperty(value = "关注时间")
    @Column(name = "fans_time")
    private ZonedDateTime fansTime;

    /**
     * 用户关注
     */
    @ApiModelProperty(value = "用户关注")
    @ManyToOne
    @JsonIgnoreProperties(value = "userFans", allowSetters = true)
    private UserAccount fansFrom;

    @ManyToOne
    @JsonIgnoreProperties(value = "userFans", allowSetters = true)
    private UserAccount fansTo;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getFansTime() {
        return fansTime;
    }

    public UserFans fansTime(ZonedDateTime fansTime) {
        this.fansTime = fansTime;
        return this;
    }

    public void setFansTime(ZonedDateTime fansTime) {
        this.fansTime = fansTime;
    }

    public UserAccount getFansFrom() {
        return fansFrom;
    }

    public UserFans fansFrom(UserAccount userAccount) {
        this.fansFrom = userAccount;
        return this;
    }

    public void setFansFrom(UserAccount userAccount) {
        this.fansFrom = userAccount;
    }

    public UserAccount getFansTo() {
        return fansTo;
    }

    public UserFans fansTo(UserAccount userAccount) {
        this.fansTo = userAccount;
        return this;
    }

    public void setFansTo(UserAccount userAccount) {
        this.fansTo = userAccount;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserFans)) {
            return false;
        }
        return id != null && id.equals(((UserFans) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserFans{" +
            "id=" + getId() +
            ", fansTime='" + getFansTime() + "'" +
            "}";
    }
}
