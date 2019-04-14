package com.apishop.apibstage.service;

import com.apishop.apibstage.bean.AclBean;
import com.apishop.apibstage.repository.AclRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.jws.Oneway;
import java.util.List;
import java.util.Optional;

@Service
@CacheConfig(cacheNames =  "acls")
public class AclService {

    @Autowired
    private AclRepository aclRepository;

    /**
     * 填加权限
     * @param aclBean
     * @return
     */
    @CachePut(key = "'acl' +#result.id")
    public AclBean addAcl(AclBean aclBean){
        return aclRepository.save(aclBean);
    }

    /**
     * 查询是否有某一权限
     * @param code
     * @return
     */
    public AclBean haveAcl(String code){
        return aclRepository.findByCode(code);
    }

    /**
     * 查询权限
     * @param id
     * @return
     */
    @Cacheable(key = "'acl' +#id",unless = "#result == null")
    public AclBean findAcl(int id){
        Optional<AclBean> acl = aclRepository.findById(id);
        if(acl.isPresent()){
            return acl.get();
        }
        return null;
    }


    /**
     * 查询所有权限
     * @return
     */
    public List<AclBean>  getAllRole(){
        return aclRepository.findAll();
    }


    /**
     * 修改权限信息
     * @param aclBean
     * @return
     */
    @CachePut(key = "'acl' +#result.id")
    public AclBean updateAcl(AclBean aclBean){
        return aclRepository.save(aclBean);
    }


    /**
     * 删除权限信息
     * @param id
     * @return
     */
    @CacheEvict(key = "'acl' +#id")
    public boolean deleteAcl(int id){
        if(findAcl(id) == null){
            return false;
        }
        aclRepository.deleteAcl(id);
        return true;
    }

    @CacheEvict(key = "'acl' +#aclBean.id")
    public boolean deleteAcl(AclBean aclBean){
        //删除角色权限表中的数据
        aclRepository.deleteAclRole(aclBean.getId());
        //删除权限表中的数据
        aclRepository.deleteAcl(aclBean.getId());
        return true;
    }
}
