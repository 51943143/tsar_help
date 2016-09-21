package com.tsar.server.config;

import javax.sql.DataSource;

import org.nutz.dao.Dao;
import org.nutz.dao.impl.NutDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@AutoConfigureAfter(DataSourceAutoConfiguration.class)
public class NutzDaoConfig {
	
	@Autowired
	private SpringDaoRunner runner;
	
	@Bean(name = "dao")
	@Autowired
	public Dao  nutzDao(DataSource dataSource) {
		System.out.println("...............start config nutzdao.....................");
		NutDao dao=new  NutDao(dataSource);
		dao.setRunner(runner);
		System.out.println(".................end config nutzdao.....................");
		return dao;
	}

}
