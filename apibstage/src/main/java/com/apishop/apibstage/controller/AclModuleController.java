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
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.acl.Acl;

@RestController
@RequestMapping(value = "/aclModule")
public class AclModuleController {

    @Autowired
    AclModuleService aclModuleService;

    @Autowired
    AclService aclService;

    @Autowired
    LogService logService;
    /**
     * url : 127.0.0.1:8080/aclModule/get/1
     * 查询模块信息
     * @param id
     * @return
     */
    @GetMapping(value = "/get/{id}")
    public ApiResponseUtil getModel(@PathVariable("id") int id){
        AclModuleBean aclModuleBean = aclModuleService.getAclModule(id);
        if(aclModuleBean == null){
            return new ApiResponseUtil(StatusEnum.QUERY_FAIL, MsgEnum.QUERY_FAIL);
        }
        ApiResponseUtil apiResponseUtil = new ApiResponseUtil(StatusEnum.SUCCESS,MsgEnum.QUERY_SUCCESS);
        apiResponseUtil.setData(aclModuleBean);
        return apiResponseUtil;
    }


    /**
     * url: 127.0.0.1:8080/aclModule/add
     * 参数：
     * {
     * 	"name" : "叶子节点2" ,
     * 	"parent_id" : 1 ,
     * 	"level" : "123"
     * }
     *
     * 添加权限视图
     * @param aclModuleBean
     * @param br
     * @return
     */
    @PostMapping(value = "/add")
    public ApiResponseUtil addModule(@RequestBody @Validated AclModuleBean aclModuleBean, BindingResult br){
        if(br.hasErrors()){
            ErrorUtil.printError(br);
            return new ApiResponseUtil(StatusEnum.ADD_FAIL,MsgEnum.ADD_FAIL);
        }
        if(aclModuleService.findByName(aclModuleBean.getName(),aclModuleBean.getParent_id()) != null){
            return new ApiResponseUtil(StatusEnum.ADD_FAIL,MsgEnum.MODULE_EXIST);
        }
        ApiResponseUtil apiResponseUtil =new ApiResponseUtil(StatusEnum.SUCCESS,MsgEnum.ADD_SUCCESS);
        AclModuleBean module =aclModuleService.addModule(aclModuleBean);
        apiResponseUtil.setData(module);
        //修改日志信息
        logService.updateLog(TypeEnum.ACLMODULE,module.getId(), Helper.EMPTY_STRING,module.toString());
        return apiResponseUtil;
    }


    /**
     *  url:    127.0.0.1:8080/aclModule/update/4
     * 参数：
     * {
     * 	"name" : "叶子节点7",
     *      "level" : "123"
     * }
     * 修改模块视图
     * @param id
     * @param aclModuleBean
     * @param br
     * @return
     */
    @PostMapping(value = "/update/{id}")
    public ApiResponseUtil updateModule(@PathVariable("id")int id,@RequestBody @Validated AclModuleBean aclModuleBean,BindingResult br){
        if(br.hasErrors()){
            ErrorUtil.printError(br);
            return new ApiResponseUtil(StatusEnum.UPDATE_FAIL,MsgEnum.UPDATE_FAIL);
        }
        AclModuleBean oldModule = aclModuleService.getAclModule(id);
        if(oldModule == null){
            return new ApiResponseUtil(StatusEnum.UPDATE_FAIL,MsgEnum.MODULE_NOT_EXIST);
        }
        AclModuleBean aclModule = aclModuleService.findByName(aclModuleBean.getName(),aclModuleBean.getParent_id());
        if(aclModule != null && aclModule.getId() != id){
            return new ApiResponseUtil(StatusEnum.UPDATE_FAIL,MsgEnum.MODULE_EXIST);
        }
        aclModuleBean.setId(id);
        aclModuleBean.setAclBeans(oldModule.getAclBeans());
        ApiResponseUtil apiResponseUtil = new ApiResponseUtil(StatusEnum.SUCCESS,MsgEnum.UPDATE_SUCCESS);
        AclModuleBean newModule = aclModuleService.updateAclModule(aclModuleBean);
        apiResponseUtil.setData(newModule);
        logService.updateLog(TypeEnum.ACLMODULE,id,oldModule.toString(),newModule.toString());
        return apiResponseUtil;
    }


    /**
     * 查询某一层权限
     * @param id
     * @return
     */
    @GetMapping(value = "/getByParentId/{id}")
    public ApiResponseUtil getAllByParentId(@PathVariable("id") int id){
        ApiResponseUtil apiResponseUtil = new ApiResponseUtil(StatusEnum.SUCCESS,MsgEnum.QUERY_SUCCESS);
        apiResponseUtil.setData(aclModuleService.getModuleByParentId(id));
        return apiResponseUtil;
    }


    /**
     *
     * 删除模块
     * @param id
     * @return
     */
    @GetMapping(value = "/delete/{id}")
    public ApiResponseUtil deleteModule(@PathVariable("id") int id){
        AclModuleBean module = aclModuleService.getAclModule(id);
        //先删除模块下的所有权限
        for(AclBean t : module.getAclBeans()){
            aclService.deleteAcl(t);
            logService.updateLog(TypeEnum.ACL,t.getId(),t.toString(),Helper.EMPTY_STRING);
        }

        if(aclModuleService.deleteAclModule(module.getId())){
            logService.updateLog(TypeEnum.ACLMODULE,id,module.toString(),Helper.EMPTY_STRING);
            return  new ApiResponseUtil(StatusEnum.SUCCESS,MsgEnum.DELETE_SUCCESS);
        }
        return new ApiResponseUtil(StatusEnum.DELETE_FAIL,MsgEnum.DELETE_FAIL);
    }


}
