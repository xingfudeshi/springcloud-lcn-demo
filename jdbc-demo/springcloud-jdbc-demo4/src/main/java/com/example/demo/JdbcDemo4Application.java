package com.example.demo;

import com.alibaba.druid.pool.DruidDataSource;
import com.codingapi.tx.datasource.relational.LCNTransactionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

@EnableAutoConfiguration
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class JdbcDemo4Application {

	public static void main(String[] args) {
		SpringApplication.run(JdbcDemo4Application.class, args);
	}

	@Autowired
	private Environment env;

	@Bean
	public LCNTransactionDataSource dataSource() {
		DruidDataSource dataSource = new DruidDataSource();
		dataSource.setUrl(env.getProperty("spring.datasource.url"));
		dataSource.setUsername(env.getProperty("spring.datasource.username"));//用户名
		dataSource.setPassword(env.getProperty("spring.datasource.password"));//密码
		dataSource.setInitialSize(2);
		dataSource.setMaxActive(20);
		dataSource.setMinIdle(0);
		dataSource.setMaxWait(60000);
		dataSource.setValidationQuery("SELECT 1");
		dataSource.setTestOnBorrow(false);
		dataSource.setTestWhileIdle(true);
		dataSource.setPoolPreparedStatements(false);

		LCNTransactionDataSource dataSourceProxy = new LCNTransactionDataSource();
		dataSourceProxy.setDataSource(dataSource);
		dataSourceProxy.setMaxCount(10);
		return dataSourceProxy;
	}

}
