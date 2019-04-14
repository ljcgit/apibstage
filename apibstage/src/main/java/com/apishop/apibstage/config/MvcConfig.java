package com.apishop.apibstage.config;

import com.apishop.apibstage.interceptor.TokenInterceptor;
import com.apishop.apibstage.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {



    //token拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new TokenInterceptor())
                .excludePathPatterns("/auth")
                .addPathPatterns("/**");
    }
}
