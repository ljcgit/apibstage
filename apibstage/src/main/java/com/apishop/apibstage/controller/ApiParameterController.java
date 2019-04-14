package com.apishop.apibstage.controller;

import com.apishop.apibstage.bean.ApiInfoBean;
import com.apishop.apibstage.bean.ApiParameterBean;
import com.apishop.apibstage.myEnum.MsgEnum;
import com.apishop.apibstage.myEnum.StatusEnum;
import com.apishop.apibstage.myEnum.TypeEnum;
import com.apishop.apibstage.service.ApiInfoService;
import com.apishop.apibstage.service.ApiParameterService;
import com.apishop.apibstage.service.LogService;
import com.apishop.apibstage.util.ApiResponseUtil;
import com.apishop.apibstage.util.ErrorUtil;
import com.apishop.apibstage.util.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/parameter")
public class ApiParameterController {

    @Autowired
    private ApiParameterService apiParameterService;

    @Autowired
    private ApiInfoService apiInfoService;

    @Autowired
    private LogService logService;

    /**
     * 添加参数信息
     * @param infoId
     * @param apiParameterBean
     * @param br
     * @return
     */
    @PostMapping(value = "/add/{infoId}")
    public ApiResponseUtil addParameter(@PathVariable("infoId")int infoId, @RequestBody @Validated ApiParameterBean apiParameterBean, BindingResult br){

        if(br.hasErrors()){
            ErrorUtil.printError(br);
            return new ApiResponseUtil(StatusEnum.ADD_FAIL, MsgEnum.ADD_FAIL);
        }
        ApiInfoBean apiInfo =  apiInfoService.findApi(infoId);
        if(apiInfo  == null){
            return new ApiResponseUtil(StatusEnum.ADD_FAIL,MsgEnum.API_NOT_EXIST);
        }
        ApiParameterBean p = apiParameterService.findParameter(apiParameterBean.getParameter_name(),infoId);
        if(p != null){
            return new ApiResponseUtil(StatusEnum.ADD_FAIL,MsgEnum.PARAMETER_EXIST);
        }
        ApiResponseUtil apiResponseUtil = new ApiResponseUtil(StatusEnum.SUCCESS,MsgEnum.ADD_SUCCESS);
        apiParameterBean.setApiInfoBean(apiInfo);
        ApiParameterBean newParameter = apiParameterService.addParameter(apiParameterBean);
        apiResponseUtil.setData(newParameter);
        logService.updateLog(TypeEnum.API_PARAMER,newParameter.getId(), Helper.EMPTY_STRING,newParameter.toString());
        return apiResponseUtil;
    }

    /**
     * 修改参数信息
     * @param id
     * @param apiParameterBean
     * @param br
     * @return
     */
    @PostMapping(value = "/update/{id}")
    public ApiResponseUtil updateParameter(@PathVariable int id,@RequestBody @Validated ApiParameterBean apiParameterBean, BindingResult br){
        if(br.hasErrors()){
            ErrorUtil.printError(br);
            return new ApiResponseUtil(StatusEnum.ADD_FAIL, MsgEnum.ADD_FAIL);
        }
        ApiParameterBean oldParameter = apiParameterService.findParameter(id);
        if(oldParameter == null){
            return new ApiResponseUtil(StatusEnum.UPDATE_FAIL, MsgEnum.PARAMETER_NOT_EXIST);
        }
        ApiParameterBean p = apiParameterService.findParameter(apiParameterBean.getParameter_name(),oldParameter.getApiInfoBean().getId());
        if( p != null && p.getId() != id){
            return new ApiResponseUtil(StatusEnum.UPDATE_FAIL,MsgEnum.PARAMETER_EXIST);
        }
        ApiResponseUtil apiResponseUtil = new ApiResponseUtil(StatusEnum.SUCCESS,MsgEnum.UPDATE_SUCCESS);
        apiParameterBean.setApiInfoBean(oldParameter.getApiInfoBean());
        apiParameterBean.setId(id);
        ApiParameterBean newParameter = apiParameterService.updateParameter(apiParameterBean);
        logService.updateLog(TypeEnum.API_PARAMER,id,oldParameter.toString(),newParameter.toString());
        return apiResponseUtil;
    }


    /**
     * 查询参数信息
     * @param id
     * @return
     */
    @GetMapping(value = "/get/{id}")
    public ApiResponseUtil getParameter(@PathVariable("id")int id){
        ApiParameterBean parameterBean = apiParameterService.findParameter(id);
        if(parameterBean == null){
            return new ApiResponseUtil(StatusEnum.QUERY_FAIL,MsgEnum.PARAMETER_NOT_EXIST);
        }
        ApiResponseUtil apiResponseUtil = new ApiResponseUtil(StatusEnum.SUCCESS,MsgEnum.QUERY_SUCCESS);
        apiResponseUtil.setData(parameterBean);
        return apiResponseUtil;

    }


    /**
     * 查询某个api的参数
     * @param infoId
     * @return
     */
    @GetMapping(value = "/getParameterByInfo/{infoId}")
    public ApiResponseUtil getParameterByInfo(@PathVariable("infoId") int infoId){
        ApiResponseUtil apiResponseUtil = new ApiResponseUtil(StatusEnum.SUCCESS,MsgEnum.QUERY_SUCCESS);
        apiResponseUtil.setData(apiParameterService.getParameterByInfo(infoId));
        return apiResponseUtil;
    }


    @GetMapping(value = "/delete/{id}")
    public ApiResponseUtil deleteParameter(@PathVariable("id")int id){
        ApiParameterBean oldParameter = apiParameterService.findParameter(id);
        if(oldParameter == null){
            return new ApiResponseUtil(StatusEnum.DELETE_FAIL, MsgEnum.PARAMETER_NOT_EXIST);
        }
        apiParameterService.deleteParameter(id);
        logService.updateLog(TypeEnum.API_PARAMER,id,oldParameter.toString(),Helper.EMPTY_STRING);
        return new ApiResponseUtil(StatusEnum.SUCCESS,MsgEnum.DELETE_SUCCESS);
    }
}
