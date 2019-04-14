package com.apishop.apibstage.bean;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Set;

/**
 * 用户
 */

@Entity
@Table(name = "sys_user")
public class UserBean implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;        //用户id
    @NotBlank
    private String username;   //用户名
    @NotBlank
    private String password;   //密码

    private String salt;       //盐
    @Email
    private String email;      //邮箱地址

    @Column(columnDefinition="INT default 1")
    private int status = 1;       //状态
    private String remark;    //备注

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "sys_role_user",joinColumns = {@JoinColumn(name="user_id")},inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<RoleBean> roles;   //拥有的角色

    public UserBean(){}

    public UserBean(String username,String password){
        this.username = username;
        this.password = password;
    }

    public UserBean(int id, String username, String password, String salt, String email, int status, String remark) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.salt = salt;
        this.email = email;
        this.status = status;
        this.remark = remark;
    }

    public Set<RoleBean> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleBean> roles) {
        this.roles = roles;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


    @Override
    public String toString() {
        return "UserBean{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", salt='" + salt + '\'' +
                ", email='" + email + '\'' +
                ", status=" + status +
                ", remark='" + remark + '\'' +
                '}';
    }
}
