package com.apishop.apibstage.interceptor;

import com.apishop.apibstage.bean.AclBean;
import com.apishop.apibstage.bean.RoleBean;
import com.apishop.apibstage.bean.UserBean;
import com.apishop.apibstage.myEnum.MsgEnum;
import com.apishop.apibstage.myEnum.StatusEnum;
import com.apishop.apibstage.myJwt.JwtUtil;
import com.apishop.apibstage.repository.UserRepository;
import com.apishop.apibstage.util.ApiResponseUtil;
import org.springframework.beans.factory.BeanFactory;
import net.sf.json.JSONObject;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.Set;

/**
 *  Tokan拦截器
 */

public class TokenInterceptor implements HandlerInterceptor {


    //错误页面会导致显示 404（原404无法正确显示）
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setCharacterEncoding("utf-8");

        ApiResponseUtil apiResponseUtil = new ApiResponseUtil();
        String token = request.getHeader("access-token");
        //没有携带access-token信息
        if(null == token){
            apiResponseUtil.setApiResponse(StatusEnum.TOKEN_NOT_EXIST, MsgEnum.TOKEN_NOT_EXIST);
            //json化对象
            JSONObject responseJSONObject = JSONObject.fromObject(apiResponseUtil);
            response.getWriter().print(responseJSONObject);
            return false;
        }
        boolean result = JwtUtil.verify(token);
        if(!result){
            apiResponseUtil.setApiResponse(StatusEnum.AUTH_FAIL, MsgEnum.TOKEN_HAVE_ERROR);
            JSONObject responseJSONObject = JSONObject.fromObject(apiResponseUtil);
            response.getWriter().print(responseJSONObject);
            return false;
        }
        //获取用户ID
        int userId = JwtUtil.getUserId(token);

        UserRepository userRepository = getMapper(UserRepository.class,request);
        Optional<UserBean> o = userRepository.findById(userId);
        if(!o.isPresent()){
            return false;
        }
        UserBean user  = o.get();
        Set<RoleBean> roles = user.getRoles();
        //获取请求资源
        String uri = request.getRequestURI();
        for(RoleBean role : roles){
            for(AclBean acl : role.getAclBeans()){
                //用户具有相应的权限
                if(uri.startsWith(acl.getUrl())){
                    return true;
                }
            }
        }
        apiResponseUtil.setApiResponse(StatusEnum.AUTH_FAIL, MsgEnum.AUTH_FAIL);
        JSONObject responseJSONObject = JSONObject.fromObject(apiResponseUtil);
        response.getWriter().print(responseJSONObject);
        return false;
    }

    /**
     * 获取Repository类
     * @param clazz
     * @param request
     * @param <T>
     * @return
     */
    private <T> T getMapper(Class<T> clazz,HttpServletRequest request)
    {
        BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
        return factory.getBean(clazz);
    }
}
