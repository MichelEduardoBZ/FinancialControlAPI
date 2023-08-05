package com.michel.financial;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Financial Control API", version = "1", description = "Testes"))
@EnableScheduling
public class FinancialControlApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinancialControlApplication.class, args);
	}

}
