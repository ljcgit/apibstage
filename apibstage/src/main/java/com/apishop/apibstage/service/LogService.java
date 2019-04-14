package com.apishop.apibstage.service;

import com.apishop.apibstage.bean.LogBean;
import com.apishop.apibstage.myEnum.TypeEnum;
import com.apishop.apibstage.repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@CacheConfig(cacheNames = "logs")
public class LogService {


    @Autowired
    LogRepository logRepository;

    /**
     * 添加日志记录
     * @param log
     * @return
     */
    @CachePut(key = "'log' + #result.id",condition = "#result!=null")
    public LogBean addLog(LogBean log){
        if(logRepository.find(log.getType(),log.getTarget_id())==null)
            return logRepository.save(log);
        return  null;
    }


    /**
     * 日志存在时修改日志，不存在则添加
     * @param typeEnum
     * @param target_id
     * @param oldValue
     * @param newValue
     * @return
     */
    //@CachePut(key = "'log' + #result.id" ,unless = "#result == null")
    public  LogBean updateLog(TypeEnum typeEnum, int target_id, String oldValue, String newValue){
        LogBean log = new LogBean(typeEnum,target_id,oldValue,newValue);
        return logRepository.save(log);
    }



    /**
     * 查询日志
     * @param id
     * @return
     */
    //@Cacheable(key = "'log' + #id",unless = "#result==null")
    public LogBean getLog(int id){
        Optional<LogBean> log = logRepository.findById(id);
        if(log.isPresent()){
            return log.get();
        }
        return null;
    }

    /**
     * 修改日志
     * @param log
     * @return
     */
    @CachePut(key = "'log' + #result.id")
    public LogBean updateLog(LogBean log){
        return logRepository.save(log);
    }


    /**
     * 查看所有日志记录
     * @return
     */
    public List<LogBean> getAllLog(){
        return logRepository.findAll();
    }
}
