package com.apishop.apibstage.bean;

import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Set;

/**
 * 权限模块
 */
@Entity
@Table(name = "sys_acl_module")
public class AclModuleBean implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;   //权限模块id
    @NotBlank
    private String name;     //权限模块名称
    @Min(value = 1)
    private int parent_id;   //上级权限模块ID
    @NotBlank
    private String level;   //权限模块层级

    @Min(value = 0)
    private int seq = 0;   //权限模块在当前层级下的顺序，由小到大

    @Range(min = 0,max = 1)
    private int status = 1;   //状态 状态：1：正常，0：冻结

    private String remark;   //备注
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name="acl_module_id",referencedColumnName = "id")
    private Set<AclBean> aclBeans;

    public AclModuleBean(){}

    public AclModuleBean(int id, String name, int parent_id, String level, int seq, int status, String remark) {
        this.id = id;
        this.name = name;
        this.parent_id = parent_id;
        this.level = level;
        this.seq = seq;
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

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
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
        return "AclModuleBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", parent_id=" + parent_id +
                ", level='" + level + '\'' +
                ", seq=" + seq +
                ", status=" + status +
                ", remark='" + remark + '\'' +
                '}';
    }
}
