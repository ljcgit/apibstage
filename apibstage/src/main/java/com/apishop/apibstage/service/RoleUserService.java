package com.apishop.apibstage.service;

import com.apishop.apibstage.bean.RoleBean;
import com.apishop.apibstage.bean.UserBean;
import com.apishop.apibstage.repository.RoleRepository;
import com.apishop.apibstage.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

import java.util.Set;


/**
 * 用户角色管理类
 */
@Service
@CacheConfig(cacheNames = "users")
public class RoleUserService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    /***
     * 为某人分配权限
     * @param userBean
     * @param roles
     * @return
     */
    @CachePut(key = "'user' + #result.id",unless = "#result == null")
    public UserBean allotRoleForUser(UserBean userBean,Set<RoleBean> roles){
        userBean.getRoles().addAll(roles);
        return userRepository.save(userBean);
    }


    /**
     * 删除用户权限集合
     * @param userBean
     * @param roles
     * @return
     */
    @CachePut(key = "'user' + #result.id",unless = "#result == null")
    public UserBean removeRoles(UserBean userBean,Set<RoleBean> roles){
        userBean.getRoles().removeAll(roles);
        return userRepository.save(userBean);
    }


}
