package com.apishop.apibstage.controller;

import com.apishop.apibstage.bean.UserBean;
import com.apishop.apibstage.myEnum.MsgEnum;
import com.apishop.apibstage.myEnum.StatusEnum;
import com.apishop.apibstage.myEnum.TypeEnum;
import com.apishop.apibstage.service.LogService;
import com.apishop.apibstage.service.UserService;
import com.apishop.apibstage.util.ApiResponseUtil;
import com.apishop.apibstage.util.ErrorUtil;
import com.apishop.apibstage.util.Helper;
import com.apishop.apibstage.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 用户视图
 */

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    LogService logService;

    /**
     *
     * {
     * 	"username" : "ljc",
     * 	"password" : "123456"
     * }
     * 添加用户视图
     * @param userBean
     * @return
     */
    @PostMapping(value = "/add")
    public ApiResponseUtil addUser(@RequestBody @Validated UserBean userBean, BindingResult br){
        //判断是否已经存在相同用户名的用户
        if(br.hasErrors()){
            ErrorUtil.printError(br);
            return new ApiResponseUtil(StatusEnum.ADD_FAIL,MsgEnum.ADD_FAIL);
        }
        if(userService.haveUser(userBean.getUsername())!=null){
            return new ApiResponseUtil(StatusEnum.ADD_FAIL,MsgEnum.USER_EXIST);
        }
        String salt = MD5Utils.createSalt();
        String md5Password = MD5Utils.getSaltMD5(userBean.getPassword(),salt);
        userBean.setPassword(md5Password);
        userBean.setSalt(salt);
        UserBean user = userService.addUser(userBean);
        //修改日志记录
        logService.updateLog(TypeEnum.USER,user.getId(), Helper.EMPTY_STRING,user.toString());
        ApiResponseUtil apiResponseUtil = new ApiResponseUtil(StatusEnum.SUCCESS, MsgEnum.ADD_SUCCESS);
        apiResponseUtil.setData(user);
        return  apiResponseUtil;
    }


    /**
     * 修改用户信息视图
     * @param id
     * @param userBean
     * @return
     */
    @PostMapping(value = "/update/{id}")
    public ApiResponseUtil updateUser(@PathVariable("id") int id,@RequestBody @Validated  UserBean userBean,BindingResult br){
        UserBean oldUser = userService.getUser(id);
        if(oldUser == null){
            return new ApiResponseUtil(StatusEnum.UPDATE_FAIL,MsgEnum.USER_NOT_EXIST);
        }
        if(br.hasErrors()){
           ErrorUtil.printError(br);
           return new ApiResponseUtil(StatusEnum.UPDATE_FAIL,MsgEnum.UPDATE_FAIL);
        }
        UserBean u = userService.haveUser(userBean.getUsername());
        if(u != null && u.getId() != id){
            return new ApiResponseUtil(StatusEnum.UPDATE_FAIL,MsgEnum.USER_EXIST);
        }
        userBean.setPassword(oldUser.getPassword());
        userBean.setSalt(oldUser.getSalt());
        userBean.setId(id);
        UserBean user = userService.updateUser(userBean);
        logService.updateLog(TypeEnum.USER,id,oldUser.toString(),user.toString());
        return new ApiResponseUtil(StatusEnum.SUCCESS,MsgEnum.UPDATE_SUCCESS);
    }


    /**
     * 获取所有用户信息
     * @return
     */
    @GetMapping(value = "/showAll")
    public ApiResponseUtil getUserAll(){
        ApiResponseUtil apiResponseUtil = new ApiResponseUtil(StatusEnum.SUCCESS,MsgEnum.QUERY_SUCCESS);
        apiResponseUtil.setData(userService.getUserAll());
        return apiResponseUtil;
    }


    /**
     * 查询某一用户信息
     */
    @GetMapping(value = "/get/{id}")
    public ApiResponseUtil findUser(@PathVariable("id") int id){
        UserBean user = userService.getUser(id);
        if(user == null){
            return new ApiResponseUtil(StatusEnum.QUERY_FAIL,MsgEnum.QUERY_FAIL);
        }
        ApiResponseUtil apiResponseUtil = new ApiResponseUtil(StatusEnum.SUCCESS,MsgEnum.QUERY_SUCCESS);
        apiResponseUtil.setData(user);
        return apiResponseUtil;
    }


    /**
     * 删除用户视图
     * @param id
     * @return
     */
    @GetMapping(value = "/delete/{id}")
    public ApiResponseUtil deleteUser(@PathVariable("id")int id){
        UserBean user = userService.getUser(id);
        if(userService.deleteUser(id)){
            logService.updateLog(TypeEnum.USER,id,user.toString(),Helper.EMPTY_STRING);
            return new ApiResponseUtil(StatusEnum.SUCCESS,MsgEnum.DELETE_SUCCESS);
        }
        return new ApiResponseUtil(StatusEnum.DELETE_FAIL,MsgEnum.DELETE_FAIL);

    }
}
