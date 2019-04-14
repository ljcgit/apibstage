package com.apishop.apibstage.controller;

import com.apishop.apibstage.bean.UserBean;
import com.apishop.apibstage.myEnum.MsgEnum;
import com.apishop.apibstage.myEnum.StatusEnum;
import com.apishop.apibstage.myJwt.JwtUtil;
import com.apishop.apibstage.service.UserService;
import com.apishop.apibstage.util.ApiResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
@RestController
public class AuthController {

    @Autowired
    UserService userService;

    /**
     * 认证视图
     * @param map
     * @return
     */
    @PostMapping(value = "/auth")
    public Object login(@RequestBody Map<String,String> map){
        //获取用户名
        String username = map.get("username");
        //获取密码
        String password = map.get("password");
        boolean isSuccess = false;
        UserBean user = userService.haveUser(username);
        //验证信息
        if(user!=null && password.equals(user.getPassword())){
            isSuccess = true;
        }

        Map<String,String> maps = new HashMap<>();
        //登录成功
        if(isSuccess){
            //生成Token
            String  token = JwtUtil.sign(username,Integer.toString(user.getId()));
            if(token!=null){
                ApiResponseUtil apiResponseUtil = new ApiResponseUtil(StatusEnum.SUCCESS, MsgEnum.LOGIN_IN);
                maps.put("assess_token",token);
                apiResponseUtil.setData(maps);
                return apiResponseUtil;
            }
        }
        return new ApiResponseUtil(StatusEnum.LOGIN_FAIL,MsgEnum.LOGIN_FAIL);

    }
}
