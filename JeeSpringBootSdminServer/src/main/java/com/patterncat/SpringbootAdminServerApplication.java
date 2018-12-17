package com.patterncat;

import de.codecentric.boot.admin.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * http://localhost:8090
 * http://www.jianshu.com/p/e20a5f42a395
 */
@SpringBootApplication
@EnableAdminServer
public class SpringbootAdminServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootAdminServerApplication.class, args);
	}
}
