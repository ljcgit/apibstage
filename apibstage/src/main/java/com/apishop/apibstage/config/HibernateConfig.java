package com.apishop.apibstage.config;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;

@Configuration
public class HibernateConfig {

//    @Bean
//    public SessionFactory sessionFactory(@Qualifier("entityManagerFactory") EntityManagerFactory emf){
//        return emf.unwrap(SessionFactory.class);
//    }
}
