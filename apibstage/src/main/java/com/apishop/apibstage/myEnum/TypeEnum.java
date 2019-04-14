package com.apishop.apibstage.myEnum;

/**
 *    日志type字段枚举类
 *
 *    1:用户  2：权限  3：权限模块  4：角色  5：用户角色   6：权限角色
 */
public enum TypeEnum{

    USER(1),
    ACL(2),
    ACLMODULE(3),
    ROLE(4),
    USER_ROLE(5),
    ACL_ROLE(6),
    API_INFO(7),
    API_PARAMER(8),
    API_CODE(9)
    ;

    private int type;

    private TypeEnum(int type){
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
