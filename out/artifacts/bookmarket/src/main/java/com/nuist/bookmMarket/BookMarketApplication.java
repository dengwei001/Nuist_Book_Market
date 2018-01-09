package com.nuist.bookmMarket;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
@MapperScan(basePackages = "com.nuist.bookmMarket.mapper")
@Controller
public class BookMarketApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(BookMarketApplication.class);
	}
	public static void main(String[] args) {
		SpringApplication.run(BookMarketApplication.class, args);
	}

	@RequestMapping("/")
	public String index(){
		return "index";
	}
}
