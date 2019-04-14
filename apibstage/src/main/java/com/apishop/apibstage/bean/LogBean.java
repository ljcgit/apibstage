package com.apishop.apibstage.bean;


import com.apishop.apibstage.myEnum.TypeEnum;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
/**
 * 日志    问题？ 修改每次返回的是同一个对象 在数据库中存放的也是同一个对象
 */

@Entity
@Table(name = "sys_log")
public class LogBean implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    private int type;    //权限更新的类型   1:用户  2：权限  3：权限模块  4：角色  5：用户角色   6：权限角色
    @NotNull
    private int target_id;    //基于type后指定的对象id
    private String old_value;      //旧值
    private String new_value;      //新值
    @NotNull
    @Column(columnDefinition = "INT default 0")
    private int status;            //状态   0:复原过 1：没有复原过

    @NotNull
    private int operator;          //操作者
    public LogBean(){}

    public LogBean(int id, int type, int target_id, String old_value, String new_value, int status) {
        this.id = id;
        this.type = type;
        this.target_id = target_id;
        this.old_value = old_value;
        this.new_value = new_value;
        this.status = status;
    }

    public LogBean(TypeEnum typeEnum, int target_id,String old_value, String new_value ){
        this.type = typeEnum.getType();
        this.target_id = target_id;
        this.old_value = old_value;
        this.new_value = new_value;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getTarget_id() {
        return target_id;
    }

    public void setTarget_id(int target_id) {
        this.target_id = target_id;
    }

    public String getOld_value() {
        return old_value;
    }

    public void setOld_value(String old_value) {
        this.old_value = old_value;
    }

    public String getNew_value() {
        return new_value;
    }

    public void setNew_value(String new_value) {
        this.new_value = new_value;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "LogBean{" +
                "id=" + id +
                ", type=" + type +
                ", target_id=" + target_id +
                ", old_value='" + old_value + '\'' +
                ", new_value='" + new_value + '\'' +
                ", status=" + status +
                '}';
    }
}
