package com.benz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class NearbySearchBenzApplication {

	public static void main(String[] args) {
		SpringApplication.run(NearbySearchBenzApplication.class, args);
	}

}
