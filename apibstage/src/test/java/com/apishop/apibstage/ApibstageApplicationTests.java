package com.apishop.apibstage;

import com.apishop.apibstage.bean.AclBean;
import com.apishop.apibstage.bean.AclModuleBean;
import com.apishop.apibstage.service.AclModuleService;
import com.apishop.apibstage.service.AclService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Date;
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApibstageApplicationTests {

    @Test
    public void contextLoads() {

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
        System.out.println(dateFormat.format(new Date()));
    }


    @Autowired
    AclService aclService;

    @Autowired
    AclModuleService aclModuleService;
    @Test
    public void test1(){
        AclBean aclBean =new AclBean();
        aclBean.setCode("102");
        aclBean.setName("添加");
        aclBean.setUrl("/index");
        AclModuleBean aclModuleBean = aclModuleService.getAclModule(1);
        aclBean.setAclModuleBean(aclModuleBean);
        aclService.addAcl(aclBean);
    }

}
