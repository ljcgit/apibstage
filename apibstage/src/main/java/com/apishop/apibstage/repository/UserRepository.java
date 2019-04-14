package com.apishop.apibstage.repository;

import com.apishop.apibstage.bean.UserBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserBean,Integer> {

    @Query(value = "select count(1) from sys_user",nativeQuery = true)
     public int getNumOfUser();

    public UserBean findByUsername(String username);
}
