package com.apishop.apibstage.repository;

import com.apishop.apibstage.bean.LogBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRepository extends JpaRepository<LogBean,Integer> {

    @Query(value = "select * from sys_log where type = ?1 and target_id = ?2",nativeQuery = true)
    public LogBean find(int type,int id);
}
