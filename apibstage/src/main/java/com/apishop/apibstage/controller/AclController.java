package com.apishop.apibstage.controller;

import com.apishop.apibstage.bean.AclBean;
import com.apishop.apibstage.bean.AclModuleBean;
import com.apishop.apibstage.myEnum.MsgEnum;
import com.apishop.apibstage.myEnum.StatusEnum;
import com.apishop.apibstage.myEnum.TypeEnum;
import com.apishop.apibstage.service.AclModuleService;
import com.apishop.apibstage.service.AclService;
import com.apishop.apibstage.service.LogService;
import com.apishop.apibstage.util.ApiResponseUtil;
import com.apishop.apibstage.util.ErrorUtil;
import com.apishop.apibstage.util.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 权限视图
 */

@RestController
@RequestMapping(value = "/acl")
public class AclController {

    @Autowired
    private AclService aclService;

    @Autowired
    private AclModuleService aclModuleService;

    @Autowired
    private LogService logService;
    /**
     * 添加权限
     *
     *  url: 127.0.0.1:8080/acl/add/1
     *  参数：
     *  {
     * 	"code" : "105",
     * 	"name" : "lihai",
     * 	"url" : "/index"
     * }
     * @param aclBean
     * @param br
     * @return
     */
    @PostMapping(value = "/add/{moduleId}")
    public ApiResponseUtil addRole(@PathVariable("moduleId")int moduleId,@RequestBody @Validated AclBean aclBean, BindingResult br){
        if(br.hasErrors()){
            //打印错误日志
            ErrorUtil.printError(br);
            return new ApiResponseUtil(StatusEnum.ADD_FAIL, MsgEnum.ADD_FAIL);
        }
        if(aclService.haveAcl(aclBean.getCode()) != null){
            return new ApiResponseUtil(StatusEnum.ADD_FAIL, MsgEnum.ACL_EXIST);
        }
        AclModuleBean aclModuleBean = aclModuleService.getAclModule(moduleId);
        if(aclModuleBean == null){
            return new ApiResponseUtil(StatusEnum.ADD_FAIL,MsgEnum.MODULE_NOT_EXIST);
        }
        aclBean.setAclModuleBean(aclModuleBean);
        ApiResponseUtil apiResponseUtil = new ApiResponseUtil(StatusEnum.SUCCESS,MsgEnum.ADD_SUCCESS);
        AclBean acl = aclService.addAcl(aclBean);
        //更新日志
        logService.updateLog(TypeEnum.ACL,acl.getId(), Helper.EMPTY_STRING,acl.toString());
        apiResponseUtil.setData(acl);
        return apiResponseUtil;
    }


    /**
     * url: 127.0.0.1:8080/acl/updateAcl/6
     * 参数：
     * {
     * 	"code" : "105",
     * 	"name" : "qwe",
     * 	"url" : "/index",
     * 	"acl_module_id" : 1
     * }
     * 修改权限视图
     * @param id
     * @param aclBean
     * @return
     */
    @PostMapping(value = "/updateAcl/{id}")
    public ApiResponseUtil updateAcl(@PathVariable("id")int id,@RequestBody @Validated AclBean aclBean,BindingResult br){
        if(br.hasErrors()){
            ErrorUtil.printError(br);
            return new ApiResponseUtil(StatusEnum.UPDATE_FAIL,MsgEnum.ADD_FAIL);
        }
        AclBean acl = aclService.findAcl(id);
        if(acl == null){
            return new ApiResponseUtil(StatusEnum.UPDATE_FAIL,MsgEnum.ACL_NOT_EXIST);
        }
        if(aclService.haveAcl(aclBean.getCode()).getId() != id){
            return new ApiResponseUtil(StatusEnum.UPDATE_FAIL,MsgEnum.ACL_EXIST);
        }
        ApiResponseUtil apiResponseUtil = new ApiResponseUtil(StatusEnum.SUCCESS,MsgEnum.UPDATE_SUCCESS);
        aclBean.setId(id);
        aclBean.setAclModuleBean(acl.getAclModuleBean());
        AclBean newAcl = aclService.updateAcl(aclBean);
        apiResponseUtil.setData(newAcl);
        logService.updateLog(TypeEnum.ACL,id,acl.toString(),newAcl.toString());
        return apiResponseUtil;
    }


    /**
     *
     *  url: 127.0.0.1:8080/acl/get/2
     * 查询某一权限
     * @param id
     * @return
     */
    @GetMapping(value = "/get/{id}")
    public ApiResponseUtil findAcl(@PathVariable("id")int id){
        AclBean acl = aclService.findAcl(id);
        if(acl == null){
            return new ApiResponseUtil(StatusEnum.QUERY_FAIL,MsgEnum.QUERY_FAIL);
        }
        ApiResponseUtil apiResponseUtil = new ApiResponseUtil(StatusEnum.SUCCESS,MsgEnum.QUERY_SUCCESS);
        apiResponseUtil.setData(acl);
        return apiResponseUtil;
    }


    /**
     * 删除权限视图
     * @param id
     * @return
     */
    @GetMapping(value = "/delete/{id}")
    public ApiResponseUtil deleteAcl(@PathVariable("id") int id){
        AclBean aclBean = aclService.findAcl(id);
        if(aclService.deleteAcl(id)){
            logService.updateLog(TypeEnum.ACL,id,aclBean.toString(),Helper.EMPTY_STRING);
            return new ApiResponseUtil(StatusEnum.SUCCESS,MsgEnum.DELETE_SUCCESS);
        }
        return new ApiResponseUtil(StatusEnum.DELETE_FAIL,MsgEnum.DELETE_FAIL);
    }

    /**
     * 查询所有权限
     * @return
     */
    @GetMapping(value = "/showAll")
    public ApiResponseUtil showAllAcl(){
        ApiResponseUtil apiResponseUtil = new ApiResponseUtil(StatusEnum.SUCCESS,MsgEnum.QUERY_SUCCESS);
        apiResponseUtil.setData(aclService.getAllRole());
        return apiResponseUtil;
    }
}
