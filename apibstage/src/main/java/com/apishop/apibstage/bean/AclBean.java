package com.apishop.apibstage.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Objects;

/**
 * 权限
 */
@Entity
@Table(name = "sys_acl")
@JsonIgnoreProperties(value = {"aclModuleBean"})
public class AclBean implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;               //权限id
    @NotBlank
    private String code;          //权限码
    @NotBlank
    private String name;          //权限名称
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "acl_module_id")
    private AclModuleBean aclModuleBean;   //权限所在的权限模块id
    @NotBlank
    private String url;       //请求的url

    private int type = 3;        //类型 1：菜单；2：按钮；3：其他

    private int status = 1;      //状态 1：正常；0：冻结

    private int seq = 0;    //权限在当前模块下的顺序，由小到大
    private String remark;   //备注


    public AclBean(){}


    public AclBean(int id, String code, String name, String url, int type, int status, String remark) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.url = url;
        this.type = type;
        this.status = status;
        this.remark = remark;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AclModuleBean getAclModuleBean() {
        return aclModuleBean;
    }

    public void setAclModuleBean(AclModuleBean aclModuleBean) {
        this.aclModuleBean = aclModuleBean;
    }



    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    @Override
    public String toString() {
        return "AclBean{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", type=" + type +
                ", status=" + status +
                ", seq=" + seq +
                ", remark='" + remark + '\'' +
                '}';
    }

    /**
     * 根据code判断是否相等
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AclBean aclBean = (AclBean) o;
        return Objects.equals(code, aclBean.code);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result;
        return result;
    }
}
