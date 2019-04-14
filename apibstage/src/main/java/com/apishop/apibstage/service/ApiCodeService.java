package com.apishop.apibstage.service;

import com.apishop.apibstage.bean.ApiCodeBean;
import com.apishop.apibstage.repository.ApiCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@CacheConfig(cacheNames = "codes")
public class ApiCodeService {

    @Autowired
    private ApiCodeRepository apiCodeRepository;


    @CachePut(key = "'code' + #result.id", unless = "#result == null")
    public ApiCodeBean addCode(ApiCodeBean apiCodeBean){
        return apiCodeRepository.save(apiCodeBean);
    }


    @Cacheable(key = "'code' +#id")
    public ApiCodeBean findCode(int id){
        Optional<ApiCodeBean> api =  apiCodeRepository.findById(id);
        if(api.isPresent()){
            return api.get();
        }
        return null;
    }

    public ApiCodeBean findCode(String code,int info_id){
        return apiCodeRepository.findByCode(code,info_id);
    }

    @CachePut(key = "'code' +#result.id" ,unless = "#result == null")
    public ApiCodeBean updateCode(ApiCodeBean apiCodeBean){
        return apiCodeRepository.save(apiCodeBean);
    }

    @CacheEvict(key = "'code' +#id")
    public boolean deleteCode(int id){
        apiCodeRepository.deleteCode(id);
        return true;
    }


    public List<ApiCodeBean> getCodeByInfo(int infoId){
        return apiCodeRepository.findByInfoId(infoId);
    }
}

