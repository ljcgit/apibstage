package com.apishop.apibstage.controller;

import com.apishop.apibstage.bean.AclBean;
import com.apishop.apibstage.bean.RoleBean;
import com.apishop.apibstage.myEnum.MsgEnum;
import com.apishop.apibstage.myEnum.StatusEnum;
import com.apishop.apibstage.myEnum.TypeEnum;
import com.apishop.apibstage.service.LogService;
import com.apishop.apibstage.service.RoleService;
import com.apishop.apibstage.util.ApiResponseUtil;
import com.apishop.apibstage.util.ErrorUtil;
import com.apishop.apibstage.util.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private LogService logService;

    /**
     *
     * {
     * 	"name" : "修改用户"
     * }
     * 新增角色视图
     * @param roleBean
     * @return
     */
    @PostMapping(value = "/add")
    public ApiResponseUtil addRole(@RequestBody @Validated RoleBean roleBean, BindingResult br){
        if(br.hasErrors()){
            ErrorUtil.printError(br);
            return new ApiResponseUtil(StatusEnum.ADD_FAIL, MsgEnum.ADD_FAIL);
        }
        if(roleService.haveRole(roleBean.getName()) != null){
            return new ApiResponseUtil(StatusEnum.ADD_FAIL, MsgEnum.ROLE_EXIST);
        }
        ApiResponseUtil apiResponseUtil = new ApiResponseUtil(StatusEnum.SUCCESS,MsgEnum.ADD_SUCCESS);
        RoleBean role = roleService.addRole(roleBean);
        logService.updateLog(TypeEnum.ROLE,role.getId(), Helper.EMPTY_STRING,role.toString());
        apiResponseUtil.setData(role);
        return  apiResponseUtil;
    }


    /**
     * 显示所有角色
     * @return
     */
    @GetMapping("/showAll")
    public ApiResponseUtil showAllRole(){
        ApiResponseUtil apiResponseUtil = new ApiResponseUtil(StatusEnum.SUCCESS,MsgEnum.QUERY_SUCCESS);
        apiResponseUtil.setData(roleService.getAllRole());
        return apiResponseUtil;
    }


    /**
     * 更新角色视图
     * @param id
     * @param roleBean
     * @return
     */
    @PostMapping(value = "/update/{id}")
    public ApiResponseUtil updateRole(@PathVariable("id") int id,@RequestBody @Validated  RoleBean roleBean, BindingResult br){
        if(br.hasErrors()){
            ErrorUtil.printError(br);
            return new ApiResponseUtil(StatusEnum.UPDATE_FAIL,MsgEnum.ADD_FAIL);
        }
        RoleBean oldRole = roleService.findRole(id);
        if(oldRole == null){
            return new ApiResponseUtil(StatusEnum.UPDATE_FAIL,MsgEnum.ROLE_NOT_EXIST);
        }
        if(roleService.haveRole(roleBean.getName()).getId() != id){
            return  new ApiResponseUtil(StatusEnum.UPDATE_FAIL,MsgEnum.ROLE_EXIST);
        }
        roleBean.setId(id);
        ApiResponseUtil apiResponseUtil = new ApiResponseUtil(StatusEnum.SUCCESS,MsgEnum.UPDATE_SUCCESS);
        RoleBean newRole = roleService.updateRole(roleBean);
        logService.updateLog(TypeEnum.ROLE,id,oldRole.toString(),newRole.toString());
        return apiResponseUtil;
    }


    /**
     * 查询角色信息
     * @param id
     * @return
     */
    @GetMapping(value = "/get/{id}")
    public ApiResponseUtil findRole(@PathVariable("id")int id){
        RoleBean role = roleService.findRole(id);
        if(role == null){
            return new ApiResponseUtil(StatusEnum.QUERY_FAIL,MsgEnum.QUERY_FAIL);
        }
        ApiResponseUtil apiResponseUtil = new ApiResponseUtil(StatusEnum.SUCCESS,MsgEnum.QUERY_SUCCESS);
        apiResponseUtil.setData(role);
        return apiResponseUtil;
    }

    /**
     * 删除角色视图
     * @param id
     * @return
     */
    @GetMapping(value = "/delete/{id}")
    public ApiResponseUtil deleteRole(@PathVariable("id")int id){
        RoleBean roleBean = roleService.findRole(id);
        if(roleService.deleteRole(id)){
            logService.updateLog(TypeEnum.ROLE,id,roleBean.toString(),Helper.EMPTY_STRING);
            return new ApiResponseUtil(StatusEnum.SUCCESS,MsgEnum.DELETE_SUCCESS);
        }
        return new ApiResponseUtil(StatusEnum.DELETE_FAIL,MsgEnum.DELETE_FAIL);
    }
}
