package com.apishop.apibstage.repository;

import com.apishop.apibstage.bean.ApiCodeBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ApiCodeRepository extends JpaRepository<ApiCodeBean,Integer> {

    @Query(value = "select * from api_code where status_code = ?1 and info_id = ?2",nativeQuery = true)
    public ApiCodeBean findByCode(String code,int info_id);

    @Query(value = "select * from api_code where info_id = ?",nativeQuery = true)
    public List<ApiCodeBean> findByInfoId(int infoId);

    @Transactional
    @Modifying
    @Query(value = "delete from api_code where id = ?1",nativeQuery = true)
    public void deleteCode(int id);
}
