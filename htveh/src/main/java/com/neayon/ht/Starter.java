package com.neayon.ht;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
@ServletComponentScan
@MapperScan("com.neayon.ht.dao")
public class Starter extends SpringBootServletInitializer {

	/**
	 * 部署外部tomcat必须
	 */
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(Starter.class);
	}
	
	public static void main(String[] args) {
		SpringApplication.run(Starter.class, args);
	}
}
