package com.apishop.apibstage.service;

import com.apishop.apibstage.bean.ApiParameterBean;
import com.apishop.apibstage.repository.ApiParameterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@CacheConfig(cacheNames = "parameters")
public class ApiParameterService {

    @Autowired
    private ApiParameterRepository apiParameterRepository;

    @CachePut(key = "'parameter' + #result.id" ,unless = "#result == null")
    public ApiParameterBean addParameter(ApiParameterBean apiParameterBean){
        return apiParameterRepository.save(apiParameterBean);
    }

    @CachePut(key = "'parameter' + #result.id" ,unless = "#result == null")
    public ApiParameterBean updateParameter(ApiParameterBean apiParameterBean){
        return apiParameterRepository.save(apiParameterBean);
    }

    @Cacheable(key = "'parameter' + #id")
    public ApiParameterBean findParameter(int id){
        Optional<ApiParameterBean> parameter = apiParameterRepository.findById(id);
        if(parameter.isPresent()){
            return parameter.get();
        }
        return null;
    }


    public ApiParameterBean findParameter(String parameter_name,int info_id){
        return apiParameterRepository.findByName(parameter_name,info_id);
    }

    public List<ApiParameterBean> getParameterByInfo(int infoId){
        return apiParameterRepository.getParameterByInfo(infoId);
    }


    @CacheEvict(key =  "'parameter' + #id")
    public boolean deleteParameter(int id){
        apiParameterRepository.deleteParameter(id);
        return true;
    }
}
