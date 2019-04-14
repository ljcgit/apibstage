package com.apishop.apibstage.controller;

import com.apishop.apibstage.bean.ApiCodeBean;
import com.apishop.apibstage.bean.ApiInfoBean;
import com.apishop.apibstage.bean.ApiParameterBean;
import com.apishop.apibstage.myEnum.MsgEnum;
import com.apishop.apibstage.myEnum.StatusEnum;
import com.apishop.apibstage.myEnum.TypeEnum;
import com.apishop.apibstage.service.ApiInfoService;
import com.apishop.apibstage.service.LogService;
import com.apishop.apibstage.util.ApiResponseUtil;
import com.apishop.apibstage.util.Helper;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping(value = "/api")
public class ApiInfoController {

    @Autowired
    private ApiInfoService apiInfoService;

    @Autowired
    private LogService logService;

    /**
     * 添加Api信息
     * @param maps
     * @return
     */
    @PostMapping(value = "/add")
    public ApiResponseUtil addApi(@RequestBody Map<String,Object> maps){
        JSONObject jsonObject= JSONObject.fromObject(maps.get("apiInfo"));
        ApiInfoBean stu=(ApiInfoBean) JSONObject.toBean(jsonObject, ApiInfoBean.class);

        if( apiInfoService.findApi(stu.getName()) != null){
            return new ApiResponseUtil(StatusEnum.ADD_FAIL,MsgEnum.API_EXIST);
        }

        //添加参数信息
        JSONArray jsonArray= JSONArray.fromObject(maps.get("parameters"));
        Set<ApiParameterBean> parameters = new HashSet<>();
        Object[]  p = jsonArray.toArray();
        for(int i=0;i<p.length;i++){
            JSONObject jsonObject2=JSONObject.fromObject(p[i]);
            ApiParameterBean stu2=(ApiParameterBean) JSONObject.toBean(jsonObject2, ApiParameterBean.class);
            parameters.add(stu2);
        }
        stu.setParameters(parameters);

        //添加状态码信息
        JSONArray array = JSONArray.fromObject(maps.get("codes"));
        Set<ApiCodeBean> codes = new HashSet<>();
        Object[]  c = array.toArray();
        for(int i=0;i<c.length;i++){
            JSONObject jsonObject2=JSONObject.fromObject(c[i]);
            ApiCodeBean stu2=(ApiCodeBean) JSONObject.toBean(jsonObject2, ApiCodeBean.class);
            codes.add(stu2);
        }
        stu.setCodes(codes);

        ApiInfoBean newApi = apiInfoService.addApi(stu);

        ApiResponseUtil apiResponseUtil = new ApiResponseUtil(StatusEnum.SUCCESS, MsgEnum.ADD_SUCCESS);
        //更新日志信息
        logService.updateLog(TypeEnum.API_INFO,newApi.getId(), Helper.EMPTY_STRING,newApi.toString());
        return apiResponseUtil;
    }

    /**
     * 查询Api信息
     * @param id
     * @return
     */
    @GetMapping(value = "/get/{id}")
    public ApiResponseUtil getApiInfo(@PathVariable("id")int id){
        ApiInfoBean api = apiInfoService.findApi(id);
        if(api == null){
            return new ApiResponseUtil(StatusEnum.QUERY_FAIL,MsgEnum.API_NOT_EXIST);
        }
        ApiResponseUtil apiResponseUtil = new ApiResponseUtil(StatusEnum.SUCCESS,MsgEnum.QUERY_SUCCESS);
        apiResponseUtil.setData(api);
        return apiResponseUtil;
    }

    /**
     * 查询所有Api信息
     * @return
     */
    @GetMapping(value = "/showAll")
    public ApiResponseUtil showAll(){
        ApiResponseUtil apiResponseUtil = new ApiResponseUtil(StatusEnum.SUCCESS,MsgEnum.QUERY_SUCCESS);
        apiResponseUtil.setData(apiInfoService.getAllApi());
        return apiResponseUtil;
    }


    /**
     *  修改Api信息
     * @param id
     * @param apiInfoBean
     * @param br
     * @return
     */
    @PostMapping(value = "/update/{id}")
    public ApiResponseUtil updateApi(@PathVariable("id")int id, @RequestBody @Validated ApiInfoBean apiInfoBean, BindingResult br){
        if(br.hasErrors()){
            return new ApiResponseUtil(StatusEnum.UPDATE_FAIL,MsgEnum.UPDATE_FAIL);
        }
        ApiInfoBean oldApi = apiInfoService.findApi(id);
        if(oldApi == null){
            return new ApiResponseUtil(StatusEnum.UPDATE_FAIL,MsgEnum.API_NOT_EXIST);
        }
        ApiInfoBean apiInfo = apiInfoService.findApi(apiInfoBean.getName());
        if(apiInfo != null && apiInfo.getId() != id){
            return new ApiResponseUtil(StatusEnum.UPDATE_FAIL,MsgEnum.API_EXIST);
        }
        apiInfoBean.setId(id);
        apiInfoService.updateApi(apiInfoBean);
        //更新日志信息
        logService.updateLog(TypeEnum.API_INFO,id,oldApi.toString(),apiInfoBean.toString());
        return new ApiResponseUtil(StatusEnum.SUCCESS,MsgEnum.UPDATE_SUCCESS);
    }

    /**
     * 根据状态信息查询
     * @param status
     * @return
     */
    @GetMapping(value = "/getByStatus/{status}")
    public ApiResponseUtil getApisByStatus(@PathVariable("status")int status){
        //状态值不在范围内
        if(status <0 || status > 2){
            return new ApiResponseUtil(StatusEnum.QUERY_FAIL,MsgEnum.STATUS_ERROR);
        }
        ApiResponseUtil apiResponseUtil = new ApiResponseUtil(StatusEnum.SUCCESS,MsgEnum.QUERY_SUCCESS);
        apiResponseUtil.setData(apiInfoService.getApisByStatus(status));
        return apiResponseUtil;
    }

    /**
     * 修改Api状态信息
     * @param id
     * @return
     */
    @GetMapping(value = "/updateStatus/{id}")
    public ApiResponseUtil updateApiStatus(@PathVariable("id")int id){
        ApiInfoBean oldApi = apiInfoService.findApi(id);
        if(oldApi.getStatus() <0 || oldApi.getStatus() > 1){
            return new ApiResponseUtil(StatusEnum.UPDATE_FAIL,MsgEnum.STATUS_ERROR);
        }
        oldApi.setStatus(oldApi.getStatus()+1);
        ApiInfoBean newApi = apiInfoService.updateApi(oldApi);
        //修改日志信息
        logService.updateLog(TypeEnum.API_INFO,id,oldApi.toString(),newApi.toString());
        return new ApiResponseUtil(StatusEnum.SUCCESS,MsgEnum.UPDATE_SUCCESS);
    }


    /**
     * 删除Api
     * @param id
     * @return
     */
    @GetMapping(value = "/delete/{id}")
    public ApiResponseUtil deleteApi(@PathVariable("id")int id){
        ApiInfoBean oldApi = apiInfoService.findApi(id);
        if(oldApi == null ){
            return new ApiResponseUtil(StatusEnum.DELETE_FAIL,MsgEnum.API_NOT_EXIST);
        }
        apiInfoService.deleteApi(id);
        logService.updateLog(TypeEnum.API_INFO,id,oldApi.toString(),Helper.EMPTY_STRING);
        return new ApiResponseUtil(StatusEnum.SUCCESS,MsgEnum.DELETE_SUCCESS);
    }

}
