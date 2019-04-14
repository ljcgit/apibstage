package com.apishop.apibstage.bean;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

/**
 * 角色
 */
@Entity
@Table(name = "sys_role")
//@JsonIgnoreProperties(value = {"aclBeans"})
public class RoleBean implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;   //角色id
    @NotBlank
    private String name;   //角色名称

    @Column(columnDefinition = "INT default 1")
    private int type = 1;   //角色的类型   1：普通   2：其他

    @Column(columnDefinition = "INT default 1")
    private int status = 1;   //状态   1:可用  0：冻结
    private String remark;   //备注

    @ManyToMany(cascade=CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinTable(name = "sys_role_acl",joinColumns = {@JoinColumn(name="role_id")},inverseJoinColumns = {@JoinColumn(name = "acl_id")})
    private Set<AclBean> aclBeans;   //角色拥有的权限

    public RoleBean(){}

    public RoleBean(int id, String name, int type, int status, String remark) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.status = status;
        this.remark = remark;
    }

    public Set<AclBean> getAclBeans() {
        return aclBeans;
    }

    public void setAclBeans(Set<AclBean> aclBeans) {
        this.aclBeans = aclBeans;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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
        return "RoleBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", status=" + status +
                ", remark='" + remark + '\'' +
                '}';
    }


    /**
     * 重写equals和hashCode方法，通过name来判断两个对象是否相等
     * @param o
     * @return
     */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoleBean roleBean = (RoleBean) o;
        return Objects.equals(name, roleBean.name);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result ;
        return result;
    }
}
