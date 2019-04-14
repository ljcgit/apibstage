package com.apishop.apibstage.service;

import com.apishop.apibstage.bean.RoleBean;
import com.apishop.apibstage.repository.RoleRepository;
import javafx.beans.property.ReadOnlyListProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@CacheConfig(cacheNames = "roles")
public class RoleService {

    @Autowired
    RoleRepository roleRepository;

    /**
     * 查找所有角色信息
     * @return
     */
    public List<RoleBean> getAllRole(){
        return  roleRepository.findAll();
    }

    /**
     * 查询角色信息
     * @param id
     * @return
     */
    @Cacheable(key = "'role' + #id",unless ="#result == null")
    public RoleBean findRole(int id){
        Optional<RoleBean> role = roleRepository.findById(id);
        if(role.isPresent()){
            return role.get();
        }
        return null;
    }

    /**
     * 新增角色信息
     * @param roleBean
     * @return
     */
    @CachePut(key =  "'role' + #result.id",unless = "#result==null")
    public  RoleBean addRole(RoleBean roleBean){
          return roleRepository.save(roleBean);
    }

    /**
     * 修改角色信息
     * @param roleBean
     * @return
     */
    @CachePut(key = "'role' + #roleBean.id")
    public RoleBean updateRole(RoleBean roleBean){
        return roleRepository.save(roleBean);
    }

    /**
     * 删除角色信息
     * @param id
     * @return
     */
    @CacheEvict(key = "'role' + #id")
    public boolean deleteRole(int id){
        if(findRole(id)!= null){
            //删除用户角色表中的数据
            roleRepository.deleteRoleUser(id);
            //删除角色表中的数据
            roleRepository.deleteById(id);
            return true;
        }
        return false;
    }


    /**
     * 查询是否具有某权限
     * @param name
     * @return
     */
    public RoleBean haveRole(String name){
        return roleRepository.findByName(name);
    }
}
