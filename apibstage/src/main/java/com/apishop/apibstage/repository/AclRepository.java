package com.apishop.apibstage.repository;

import com.apishop.apibstage.bean.AclBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface AclRepository extends JpaRepository<AclBean,Integer> {

    public AclBean findByCode(String code);



    @Transactional
    @Modifying
    @Query(value = "delete from sys_acl where id=?1",nativeQuery = true)
    public void deleteAcl(int id);

    @Transactional
    @Modifying
    @Query(value = "delete  from sys_role_acl where acl_id = ?1",nativeQuery = true)
    public void deleteAclRole(int acl_id);
}
