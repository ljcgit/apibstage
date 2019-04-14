package com.apishop.apibstage.controller;

import com.apishop.apibstage.bean.ApiCodeBean;
import com.apishop.apibstage.bean.ApiInfoBean;
import com.apishop.apibstage.myEnum.MsgEnum;
import com.apishop.apibstage.myEnum.StatusEnum;
import com.apishop.apibstage.myEnum.TypeEnum;
import com.apishop.apibstage.service.ApiCodeService;
import com.apishop.apibstage.service.ApiInfoService;
import com.apishop.apibstage.service.LogService;
import com.apishop.apibstage.util.ApiResponseUtil;
import com.apishop.apibstage.util.ErrorUtil;
import com.apishop.apibstage.util.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/code")
public class ApiCodeController {

    @Autowired
    private ApiInfoService apiInfoService;

    @Autowired
    private ApiCodeService apiCodeService;

    @Autowired
    private LogService logService;

    /**
     * 添加状态吗信息
     * @param infoId
     * @param apiCodeBean
     * @param br
     * @return
     */
    @PostMapping(value = "/add/{infoId}")
    public ApiResponseUtil addCode(@PathVariable("infoId")int infoId, @RequestBody @Validated ApiCodeBean apiCodeBean,BindingResult br){
        if(br.hasErrors()){
            ErrorUtil.printError(br);
            return new ApiResponseUtil(StatusEnum.ADD_FAIL, MsgEnum.ADD_FAIL);
        }
        ApiInfoBean apiInfoBean = apiInfoService.findApi(infoId);
        if(apiInfoBean == null){
            return new ApiResponseUtil(StatusEnum.ADD_FAIL,MsgEnum.API_NOT_EXIST);
        }
        if(apiCodeService.findCode(apiCodeBean.getStatus_code(),infoId) != null){
            return new ApiResponseUtil(StatusEnum.ADD_FAIL,MsgEnum.API_EXIST);
        }
        ApiResponseUtil apiResponseUtil = new ApiResponseUtil(StatusEnum.SUCCESS,MsgEnum.ADD_SUCCESS);
        apiCodeBean.setApiInfoBean(apiInfoBean);
        ApiCodeBean newCode = apiCodeService.addCode(apiCodeBean);
        apiResponseUtil.setData(newCode);
        logService.updateLog(TypeEnum.API_CODE,newCode.getId(), Helper.EMPTY_STRING,newCode.toString());
        return apiResponseUtil;
    }

    /**
     * 修改字节码信息
     * @param id
     * @param apiCodeBean
     * @param br
     * @return
     */
    @PostMapping(value = "/update/{id}")
    public ApiResponseUtil updateCode(@PathVariable("id")int id,@RequestBody @Validated ApiCodeBean apiCodeBean,BindingResult br){
        if(br.hasErrors()){
            ErrorUtil.printError(br);
            return new ApiResponseUtil(StatusEnum.UPDATE_FAIL, MsgEnum.ADD_FAIL);
        }
        ApiCodeBean oldCode = apiCodeService.findCode(id);
        if(oldCode == null){
            return new ApiResponseUtil(StatusEnum.UPDATE_FAIL, MsgEnum.CODE_NOT_EXIST);
        }
        ApiCodeBean c = apiCodeService.findCode(apiCodeBean.getStatus_code(),oldCode.getApiInfoBean().getId());
        if( c != null && c.getId() != id){
            return new ApiResponseUtil(StatusEnum.UPDATE_FAIL, MsgEnum.CODE_EXIST);
        }
        apiCodeBean.setId(id);
        apiCodeBean.setApiInfoBean(oldCode.getApiInfoBean());
        ApiResponseUtil apiResponseUtil = new ApiResponseUtil(StatusEnum.SUCCESS,MsgEnum.UPDATE_SUCCESS);
        ApiCodeBean newCode = apiCodeService.updateCode(apiCodeBean);
        apiResponseUtil.setData(newCode);
        logService.updateLog(TypeEnum.API_CODE,id,oldCode.toString(),newCode.toString());
        return apiResponseUtil;
    }

    /**
     * 查询状态码信息
     * @param id
     * @return
     */
    @GetMapping(value = "/get/{id}")
    public ApiResponseUtil getCode(@PathVariable("id") int id){
        ApiCodeBean code = apiCodeService.findCode(id);
        if(code == null){
            return new ApiResponseUtil(StatusEnum.QUERY_FAIL,MsgEnum.CODE_NOT_EXIST);
        }
        ApiResponseUtil apiResponseUtil = new ApiResponseUtil(StatusEnum.SUCCESS,MsgEnum.QUERY_SUCCESS);
        apiResponseUtil.setData(code);
        return apiResponseUtil;
    }


    /**
     * 获取某个Api下的所有状态码信息
     * @param infoId
     * @return
     */
    @GetMapping(value = "/getCodeByInfo/{infoId}")
    public ApiResponseUtil getCodeByInfo(@PathVariable("infoId")int infoId){
        ApiResponseUtil apiResponseUtil = new ApiResponseUtil(StatusEnum.SUCCESS,MsgEnum.QUERY_SUCCESS);
        apiResponseUtil.setData(apiCodeService.getCodeByInfo(infoId));
        return apiResponseUtil;
    }

    /**
     * 删除状态码
     * @param id
     * @return
     */
    @GetMapping(value = "/delete/{id}")
    public ApiResponseUtil deleteCode(@PathVariable("id")int id){
        ApiCodeBean code = apiCodeService.findCode(id);
        if(code == null){
            return new ApiResponseUtil(StatusEnum.DELETE_FAIL,MsgEnum.CODE_NOT_EXIST);
        }
        apiCodeService.deleteCode(id);
        logService.updateLog(TypeEnum.API_CODE,id,code.toString(),Helper.EMPTY_STRING);
        return new ApiResponseUtil(StatusEnum.SUCCESS,MsgEnum.DELETE_SUCCESS);

    }
}
