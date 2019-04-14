package com.apishop.apibstage.repository;

import com.apishop.apibstage.bean.ApiParameterBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ApiParameterRepository extends JpaRepository<ApiParameterBean,Integer> {

    @Query(value = "select * from api_parameter where parameter_name = ?1 and info_id = ?2",nativeQuery = true)
    public ApiParameterBean findByName(String parameter_name,int info_id);

    @Query(value = "select * from api_parameter where info_id = ?1",nativeQuery = true)
    public List<ApiParameterBean> getParameterByInfo(int infoId);

    @Transactional
    @Modifying
    @Query(value = "delete from api_parameter where id = ?1",nativeQuery = true)
    public void deleteParameter(int id);
}
