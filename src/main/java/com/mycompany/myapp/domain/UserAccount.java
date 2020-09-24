package com.mycompany.myapp.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * 账户信息
 */
@ApiModel(description = "账户信息")
@Entity
@Table(name = "user_account")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UserAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 头像
     */
    @ApiModelProperty(value = "头像")
    @Column(name = "head")
    private String head;

    /**
     * 昵称
     */
    @ApiModelProperty(value = "昵称")
    @Column(name = "nick_name")
    private String nickName;

    /**
     * 生日
     */
    @ApiModelProperty(value = "生日")
    @Column(name = "birthday")
    private String birthday;

    /**
     * 登录名
     */
    @ApiModelProperty(value = "登录名")
    @Column(name = "login")
    private String login;

    /**
     * 简介
     */
    @ApiModelProperty(value = "简介")
    @Column(name = "introduction")
    private String introduction;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHead() {
        return head;
    }

    public UserAccount head(String head) {
        this.head = head;
        return this;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getNickName() {
        return nickName;
    }

    public UserAccount nickName(String nickName) {
        this.nickName = nickName;
        return this;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getBirthday() {
        return birthday;
    }

    public UserAccount birthday(String birthday) {
        this.birthday = birthday;
        return this;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getLogin() {
        return login;
    }

    public UserAccount login(String login) {
        this.login = login;
        return this;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getIntroduction() {
        return introduction;
    }

    public UserAccount introduction(String introduction) {
        this.introduction = introduction;
        return this;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserAccount)) {
            return false;
        }
        return id != null && id.equals(((UserAccount) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserAccount{" +
            "id=" + getId() +
            ", head='" + getHead() + "'" +
            ", nickName='" + getNickName() + "'" +
            ", birthday='" + getBirthday() + "'" +
            ", login='" + getLogin() + "'" +
            ", introduction='" + getIntroduction() + "'" +
            "}";
    }
}
