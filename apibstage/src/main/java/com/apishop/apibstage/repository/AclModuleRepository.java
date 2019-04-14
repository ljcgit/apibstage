package com.apishop.apibstage.repository;

import com.apishop.apibstage.bean.AclModuleBean;
import com.apishop.apibstage.service.AclModuleService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface AclModuleRepository extends JpaRepository<AclModuleBean,Integer> {

    @Query(value = "select * from sys_acl_module where name = ?1 and parent_id = ?2",nativeQuery = true)
    public AclModuleBean findByName(String name,int parent_id);

    @Query(value = "select * from sys_acl_module where parent_id = ?1",nativeQuery = true)
    public List<AclModuleBean> findByParentId(int parentId);


}
