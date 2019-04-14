package com.apishop.apibstage.controller;

import com.apishop.apibstage.bean.AclBean;
import com.apishop.apibstage.bean.RoleBean;
import com.apishop.apibstage.myEnum.MsgEnum;
import com.apishop.apibstage.myEnum.StatusEnum;
import com.apishop.apibstage.service.AclService;
import com.apishop.apibstage.service.RoleAclService;
import com.apishop.apibstage.service.RoleService;
import com.apishop.apibstage.util.ApiResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping(value = "/roleAcl")
public class RoleAclController {

    @Autowired
    RoleService roleService;

    @Autowired
    AclService aclService;

    @Autowired
    RoleAclService roleAclService;

    /**
     * url：127.0.0.1:8080/roleAcl/allot/1
     * 参数：
     * {
     * 	"acls" : [2,3,4,5]
     * }
     *
     * 分配权限视图
     * @param id
     * @param maps
     * @return
     */
    @PostMapping(value = "/allot/{id}")
    public ApiResponseUtil allotAclForRole(@PathVariable("id") int id, @RequestBody Map<String,Object> maps){
        List<Integer>  lists = (List<Integer>) maps.get("acls");
        RoleBean role = roleService.findRole(id);
        Set<AclBean> acls = new HashSet<>(0);
        for(Integer t : lists){
            AclBean acl = aclService.findAcl(t);
            if(acl != null){
                acls.add(acl);
            }
        }
        if(role == null){
            return new ApiResponseUtil(StatusEnum.ROLE_NOT_EXIST, MsgEnum.ROLE_NOT_EXIST);
        }
        roleAclService.allotAclForRole(role,acls);
        return  new ApiResponseUtil(StatusEnum.SUCCESS,MsgEnum.ALLOT_SUCCESS);
    }


    /**
     * url:
     * 获取角色的权限
     * @param id
     * @return
     */
    @GetMapping(value = "/getAcls/{id}")
    public ApiResponseUtil getAclOfRole(@PathVariable("id")int id){
        RoleBean roleBean = roleService.findRole(id);
        if(roleBean == null){
            return new ApiResponseUtil(StatusEnum.QUERY_FAIL, MsgEnum.QUERY_FAIL);
        }
        ApiResponseUtil apiResponseUtil = new ApiResponseUtil(StatusEnum.SUCCESS,MsgEnum.QUERY_SUCCESS);
        apiResponseUtil.setData(roleBean.getAclBeans());
        return apiResponseUtil;
    }


    /**
     * 删除角色权限集合
     * @param id
     * @param maps
     * @return
     */
    @PostMapping(value = "/removeAcls/{id}")
    public ApiResponseUtil removeAcls(@PathVariable("id")int id,@RequestBody Map<String,Object> maps){
        List<Integer>  lists = (List<Integer>) maps.get("acls");
        RoleBean role = roleService.findRole(id);
        Set<AclBean> acls = new HashSet<>(0);
        for(Integer t : lists){
            AclBean acl = aclService.findAcl(t);
            if(acl != null){
                acls.add(acl);
            }
        }
        if(role == null){
            return new ApiResponseUtil(StatusEnum.ROLE_NOT_EXIST, MsgEnum.ROLE_NOT_EXIST);
        }
        roleAclService.removeAcls(role,acls);
        return  new ApiResponseUtil(StatusEnum.SUCCESS,MsgEnum.DELETE_ACL_SUCCESS);

    }
}
