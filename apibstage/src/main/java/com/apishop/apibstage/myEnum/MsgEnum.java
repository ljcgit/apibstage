package com.apishop.apibstage.myEnum;

/**
 * 状态码信息枚举类2
 */

public enum MsgEnum {

    TOKEN_NOT_EXIST("没有token信息！"),
    TOKEN_HAVE_ERROR("token信息有误！"),
    AUTH_FAIL("用户没有该权限！"),
    LOGIN_IN("登录成功！"),
    USER_EXIST("已经存在该用户!"),
    USER_NOT_EXIST("用户不存在!"),
    ROLE_EXIST("已存在该角色！"),
    ROLE_NOT_EXIST("角色不存在！"),
    ACL_EXIST("已存在该权限!"),
    ACL_NOT_EXIST("权限不存在!"),
    MODULE_EXIST("模块已存在！"),
    MODULE_NOT_EXIST("模块不存在！"),
    LOG_NOT_EXIST("该日志记录不存在！"),
    API_EXIST("api已经存在！"),
    API_NOT_EXIST("api不存在！"),
    CODE_EXIST("CODE已经存在！"),
    CODE_NOT_EXIST("CODE不存在！"),
    PARAMETER_EXIST("PARAMETER已经存在！"),
    PARAMETER_NOT_EXIST("PARAMETER不存在！"),
    LOGIN_FAIL("用户名，密码错误或者账号不存在！"),
    UPDATE_SUCCESS("修改成功！"),
    UPDATE_FAIL("修改失败！"),
    QUERY_SUCCESS("查询成功！"),
    QUERY_FAIL("查询失败"),
    ADD_FAIL("输入信息格式不正确"),
    ADD_SUCCESS("添加成功"),
    DELETE_SUCCESS("删除成功！"),
    DELETE_FAIL("删除失败！"),
    DELETE_ROLE_SUCCESS("删除角色成功！"),
    DELETE_ROLE_FAIL("删除角色失败！"),
    DELETE_ACL_SUCCESS("删除权限成功！"),
    DELETE_ACL_FAIL("删除权限失败！"),
    USER_HAVE_NO_ROLE("用户不具备该角色"),
    ALLOT_SUCCESS("分配成功！"),
    ALLOT_FAIL("分配失败！"),
    STATUS_ERROR("状态信息值错误！");



    private String msg;
    private MsgEnum(String msg){
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
