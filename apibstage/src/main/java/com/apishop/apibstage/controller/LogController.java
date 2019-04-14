package com.apishop.apibstage.controller;

import com.apishop.apibstage.bean.LogBean;
import com.apishop.apibstage.myEnum.MsgEnum;
import com.apishop.apibstage.myEnum.StatusEnum;
import com.apishop.apibstage.service.LogService;
import com.apishop.apibstage.util.ApiResponseUtil;
import javafx.scene.chart.ValueAxis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/log")
public class LogController {

    @Autowired
    private LogService logService;

    /**
     * 查询单条日志
     * @param id
     * @return
     */
    @GetMapping(value = "/get/{id}")
    public ApiResponseUtil getLog(@PathVariable("id")int id){
        LogBean log = logService.getLog(id);
        if(log == null){
            return new ApiResponseUtil(StatusEnum.QUERY_FAIL, MsgEnum.LOG_NOT_EXIST);
        }
        ApiResponseUtil apiResponseUtil = new ApiResponseUtil(StatusEnum.SUCCESS,MsgEnum.QUERY_SUCCESS);
        apiResponseUtil.setData(log);
        return apiResponseUtil;
    }


    /**
     * 查询所有日志记录
     * @return
     */
    @GetMapping(value = "/showAll")
    public ApiResponseUtil showAll(){
        ApiResponseUtil apiResponseUtil =  new ApiResponseUtil(StatusEnum.SUCCESS,MsgEnum.QUERY_SUCCESS);
        apiResponseUtil.setData(logService.getAllLog());
        return apiResponseUtil;
    }

}

