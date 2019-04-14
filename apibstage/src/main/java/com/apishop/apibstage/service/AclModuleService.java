package com.apishop.apibstage.service;

import com.apishop.apibstage.bean.AclModuleBean;
import com.apishop.apibstage.repository.AclModuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.security.acl.Acl;
import java.util.List;
import java.util.Optional;

@Service
@CacheConfig(cacheNames = "aclModule")
public class AclModuleService {

    @Autowired
    AclModuleRepository aclModuleRepository;

    @Cacheable(key = "'aclModule' +#id", unless = "#result != null")
    public AclModuleBean getAclModule(int id){
        Optional<AclModuleBean> aclModule = aclModuleRepository.findById(id);
        if(aclModule.isPresent()){
            return aclModule.get();
        }
        return null;
    }

    @CachePut(key = "'aclModule' +#result.id")
    public AclModuleBean updateAclModule(AclModuleBean aclModuleBean){
        return aclModuleRepository.save(aclModuleBean);
    }


    /**
     * 删除模块
     * @param id
     * @return
     */
    @CacheEvict(key = "'aclModule' +#id")
    public boolean deleteAclModule(int id){
        if(getAclModule(id) ==null){
            return false;
        }
        aclModuleRepository.deleteById(id);
        return true;
    }

    @CacheEvict(key = "'aclModule' +#aclModuleBean.id")
    public boolean deleteAclModule(AclModuleBean aclModuleBean){
        if(aclModuleBean == null){
            return false;
        }
        aclModuleRepository.delete(aclModuleBean);
        return true;
    }
    /**
     * 查询是否存在相同模块
     * @param name
     * @param pid
     * @return
     */
    public AclModuleBean findByName(String name,int pid){
        return aclModuleRepository.findByName(name,pid);
    }


    /**
     * 添加权限
     * @param aclModuleBean
     * @return
     */
    @CachePut(key = "'aclModule' +#result.id",unless = "#result != null")
    public AclModuleBean addModule(AclModuleBean aclModuleBean){
        return aclModuleRepository.save(aclModuleBean);
    }

    /**
     * 查询某一层权限
     * @param id
     * @return
     */
    public List<AclModuleBean> getModuleByParentId(int id){
        return aclModuleRepository.findByParentId(id);
    }
}
