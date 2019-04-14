package com.apishop.apibstage.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.support.OpenSessionInViewFilter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

@Configuration
public class FilterConfig {

//    @Bean
//    public FilterRegistrationBean myFilter(){
//        OpenSessionInViewFilter os = new OpenSessionInViewFilter();
//        os.setSessionFactoryBeanName("sessionFactory");
//        FilterRegistrationBean registrationBean = new FilterRegistrationBean(os);
//        registrationBean.setUrlPatterns(new ArrayList<String>(Arrays.asList("/*")));
//        registrationBean.setName("myFilter");
//        registrationBean.setOrder(1);
//        return registrationBean;
//    }
}

//    "message": "Could not write JSON: failed to lazily initialize a collection of role: com.apishop.apibstage.bean.RoleBean.aclBeans, could not initialize proxy - no Session; nested exception is com.fasterxml.jackson.databind.JsonMappingException: failed to lazily initialize a collection of role: com.apishop.apibstage.bean.RoleBean.aclBeans, could not initialize proxy - no Session (through reference chain: com.apishop.apibstage.util.ApiResponseUtil[\"data\"])",