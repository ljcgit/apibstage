package com.apishop.apibstage.repository;

import com.apishop.apibstage.bean.ApiInfoBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApiInfoRepository extends JpaRepository<ApiInfoBean,Integer> {

    public ApiInfoBean findByName(String name);

    public List<ApiInfoBean> findByStatus(int status);
}
