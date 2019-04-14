package com.apishop.apibstage.service;

import com.apishop.apibstage.bean.ApiInfoBean;
import com.apishop.apibstage.repository.ApiInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@CacheConfig(cacheNames = "api")
public class ApiInfoService {

    @Autowired
    ApiInfoRepository apiInfoRepository;


    @CachePut(key = "'api' + #result.id",unless = "#result == null")
    public ApiInfoBean addApi( ApiInfoBean apiInfoBean){
        return apiInfoRepository.save(apiInfoBean);
    }


    @Cacheable(key = "'api' + #id")
    public ApiInfoBean findApi(int id){
        Optional<ApiInfoBean> op = apiInfoRepository.findById(id);
        if(op.isPresent()){
            return op.get();
        }
        return null;
    }

    public ApiInfoBean findApi(String name){
        ApiInfoBean flag = apiInfoRepository.findByName(name);
        if(flag == null){
            return null;
        }
        return flag;
    }

    @CachePut(key = "'api' + #result.id",unless = "#result == null")
    public ApiInfoBean updateApi(ApiInfoBean api){
        return apiInfoRepository.save(api);
    }


    @CacheEvict(key = "'api' + #id")
    public boolean deleteApi(int id){
        apiInfoRepository.deleteById(id);
        return true;
    }

    public List<ApiInfoBean> getAllApi(){
        return apiInfoRepository.findAll();
    }

    public List<ApiInfoBean> getApisByStatus(int status){
        return apiInfoRepository.findByStatus(status);
    }
}
