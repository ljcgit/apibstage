package com.apishop.apibstage.controller;

import com.apishop.apibstage.bean.RoleBean;
import com.apishop.apibstage.bean.UserBean;
import com.apishop.apibstage.myEnum.MsgEnum;
import com.apishop.apibstage.myEnum.StatusEnum;
import com.apishop.apibstage.myEnum.TypeEnum;
import com.apishop.apibstage.service.LogService;
import com.apishop.apibstage.service.RoleService;
import com.apishop.apibstage.service.RoleUserService;
import com.apishop.apibstage.service.UserService;
import com.apishop.apibstage.util.ApiResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(value = "/roleUser")
public class RoleUserController {


    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    RoleUserService roleUserService;

    @Autowired
    LogService logService;
    /**
     * url: 127.0.0.1:8080/roleUser/allot/15
     * 参数：
     * {
     * 	"roles" : [1]
     * }
     * 分配角色视图
     * @param id
     * @param maps
     * @return
     */
    @PostMapping(value = "/allot/{id}")
    public ApiResponseUtil allotRole(@PathVariable("id") int id, @RequestBody Map<String,Object> maps){
        List<Integer>  lists = (List<Integer>) maps.get("roles");
        UserBean userBean = userService.getUser(id);
        Set<RoleBean> roles = new HashSet<>(0);
        for(Integer t : lists){
            RoleBean role = roleService.findRole(t);
            if(role != null) {
                roles.add(role);
            }
        }
        //如果该用户不存在
        if(userBean == null ){
            return new ApiResponseUtil(StatusEnum.USER_NOT_EXIST, MsgEnum.USER_NOT_EXIST);
        }
        Set<RoleBean> oldRoles = userBean.getRoles();
        UserBean user = roleUserService.allotRoleForUser(userBean,roles);
        logService.updateLog(TypeEnum.USER_ROLE,id,oldRoles.toString(),user.getRoles().toString());
        return new ApiResponseUtil(StatusEnum.SUCCESS,MsgEnum.ALLOT_SUCCESS);
    }


    /**
     * url: 127.0.0.1:8080/roleUser/getRoles/15
     * 查询用户具有的角色
     * @param id
     * @return
     */
    @GetMapping(value = "/getRoles/{id}")
    public ApiResponseUtil getRoleOfUser(@PathVariable("id") int id){
        UserBean userBean = userService.getUser(id);
        if(userBean == null){
            return new ApiResponseUtil(StatusEnum.USER_NOT_EXIST, MsgEnum.USER_NOT_EXIST);
        }
        ApiResponseUtil apiResponseUtil = new ApiResponseUtil(StatusEnum.SUCCESS,MsgEnum.QUERY_SUCCESS);
        apiResponseUtil.setData(userBean.getRoles());
        return apiResponseUtil;
    }


    /**
     *
     * 删除用户的某个角色
     * @param id
     * @param roleId
     * @return
     */
    @GetMapping(value = "/removeRole/{id}")
    public ApiResponseUtil removeRole(@PathVariable("id") int id, @RequestParam("roleId")int roleId){
        UserBean user = userService.getUser(id);
        RoleBean role = roleService.findRole(roleId);
        if(user == null || role == null ){
            return new ApiResponseUtil(StatusEnum.USER_NOT_EXIST,MsgEnum.USER_NOT_EXIST);
        }
        Set<RoleBean> roles = user.getRoles();
        if(!roles.contains(role)){
            return new ApiResponseUtil(StatusEnum.DELETE_ROLE_FAIL,MsgEnum.DELETE_ROLE_FAIL);
        }
        Set<RoleBean> oldRoles = roles;
        roles.remove(role);
        userService.updateUser(user);
        logService.updateLog(TypeEnum.USER_ROLE,id,oldRoles.toString(),roles.toString());
        return new ApiResponseUtil(StatusEnum.SUCCESS,MsgEnum.DELETE_ROLE_SUCCESS);
    }


    /**
     * url: 127.0.0.1:8080/roleUser/removeRoles/15
     * 参数：
     * {
     * 	"roles" : [1]
     * }
     * 删除用户角色集合
     * @param id
     * @param maps
     * @return
     */
    @PostMapping(value = "/removeRoles/{id}")
    public ApiResponseUtil removeSomeRole(@PathVariable("id")int id,@RequestBody Map<String,Object> maps){
        List<Integer>  lists = (List<Integer>) maps.get("roles");
        UserBean userBean = userService.getUser(id);
        Set<RoleBean> roles = new HashSet<>(0);
        for(Integer t : lists){
            RoleBean role = roleService.findRole(t);
            if(role != null) {
                roles.add(role);
            }
        }
        //如果该用户不存在
        if(userBean == null ){
            return new ApiResponseUtil(StatusEnum.USER_NOT_EXIST, MsgEnum.USER_NOT_EXIST);
        }
        Set<RoleBean> oldRoles = userBean.getRoles();
        UserBean user = roleUserService.removeRoles(userBean,roles);
        logService.updateLog(TypeEnum.USER_ROLE,id,oldRoles.toString(),user.getRoles().toString());
        return new ApiResponseUtil(StatusEnum.SUCCESS,MsgEnum.DELETE_ROLE_SUCCESS);
    }

}
