package com.apishop.apibstage.service;

import com.apishop.apibstage.bean.AclBean;
import com.apishop.apibstage.bean.RoleBean;
import com.apishop.apibstage.repository.AclRepository;
import com.apishop.apibstage.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@CacheConfig(cacheNames = "roles")
public class RoleAclService {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    AclRepository aclRepository;


    /**
     *  分配权限给角色
     * @param roleBean
     * @param aclBeans
     * @return
     */
    @CachePut(key = "'role' + #result.id", unless = "#result == null")
    public RoleBean allotAclForRole(RoleBean roleBean, Set<AclBean> aclBeans){
        roleBean.getAclBeans().addAll(aclBeans);
        return roleRepository.save(roleBean);
    }


    /**
     * url: 127.0.0.1:8080/roleAcl/removeAcls/1
     * 参数：
     * {
     * 	"acls" : [4]
     * }
     * 删除权限
     * @param roleBean
     * @param aclBeans
     * @return
     */
    @CachePut(key = "'role' + #result.id",unless = "#result == null ")
    public RoleBean removeAcls(RoleBean roleBean,Set<AclBean> aclBeans){
        System.out.println(roleBean.getAclBeans() + " :  " + aclBeans);
        roleBean.getAclBeans().removeAll(aclBeans);
        return roleRepository.save(roleBean);
    }
}
