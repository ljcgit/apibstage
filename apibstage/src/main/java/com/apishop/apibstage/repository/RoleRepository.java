package com.apishop.apibstage.repository;

import com.apishop.apibstage.bean.RoleBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface RoleRepository extends JpaRepository<RoleBean,Integer> {

    public RoleBean findByName(String name);

    @Transactional
    @Modifying
    @Query(value = "delete from sys_role_user where role_id = ?1",nativeQuery = true)
    public void deleteRoleUser(int roleId);
}
