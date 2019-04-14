package com.apishop.apibstage.service;

import com.apishop.apibstage.bean.UserBean;
import com.apishop.apibstage.repository.UserRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * 缓存key : user + id
 */
@Service
@CacheConfig(cacheNames = "users")
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * 插入用户
     * @param user
     * @return
     */
    @CachePut(key ="'user'+ #result.id",unless = "#result  eq  null")
    public UserBean addUser(UserBean user){
        return userRepository.save(user);
    }


    /**
     * 查询用户
     */
    @Cacheable(key = " 'user' + #id",unless = "#result == null")
    public UserBean getUser(int id){
        Optional<UserBean> user = userRepository.findById(id);
        if(user.isPresent()){
            return user.get();
        }
        return null;
    }


    /**
     *
     * url : 127.0.0.1:8080/user/delete/16
     *  删除用户
     * @param id
     * @return
     */
    @CacheEvict(key = " 'user' + #id")
    public boolean deleteUser(int id){
        if(getUser(id)!=null) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }


    /**
     * 查询所有用户
     * @return
     */
    public List<UserBean> getUserAll(){
        return userRepository.findAll();
    }


    /**
     * 修改用户
     * @param userBean
     * @return
     */
    @CachePut(key = " 'user' + #userBean.id")
    public UserBean updateUser(UserBean userBean){
        return userRepository.save(userBean);
    }

    /**
     * 获取用户数量
     * @return
     */
    public int getNumOfUser(){
        return userRepository.getNumOfUser();
    }

    /**
     * 是否有该用户
     * @param username
     * @return
     */
    public UserBean haveUser(String username){
        UserBean user = userRepository.findByUsername(username);
        return user;
    }

}
