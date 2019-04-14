package com.apishop.apibstage.util;

import com.apishop.apibstage.myEnum.MsgEnum;
import com.apishop.apibstage.myEnum.StatusEnum;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * 输出格式控制类
 */
public class ApiResponseUtil {

    private String status;    //状态码
    private String msg;       //状态码对应信息内容
    @JsonInclude(JsonInclude.Include.NON_NULL)     //数据为空则不显示
    private Object data;      //数据
    private String datetime;        //日期

    public ApiResponseUtil(){}

    public ApiResponseUtil(StatusEnum status,MsgEnum msg){
        this.status = status.getCode();
        this.msg = msg.getMsg();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        datetime = df.format(new Date());
    }

    public void setApiResponse(StatusEnum status,MsgEnum msg){
        this.status = status.getCode();
        this.msg = msg.getMsg();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        datetime = df.format(new Date());
    }


    public void setStatus(StatusEnum status) {
        this.status = status.getCode();
    }

    public void setMsg(MsgEnum msg) {
        this.msg = msg.getMsg();
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public Object getData() {
        return data;
    }

    public String getDatetime() {
        return datetime;
    }

    @Override
    public String toString() {
        return "ApiResponseUtil{" +
                "status='" + status + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                ", datetime=" + datetime +
                '}';
    }
}
