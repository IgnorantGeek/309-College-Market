package org.campusmarket.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;

@EnableJpaRepositories(basePackages = "org.campusmarket.db.repositories")
@SpringBootApplication
//@EnableJdbcHttpSession
public class Application
{
	public static void main(String[] args) 
	{
		SpringApplication.run(Application.class, args);
	}
}
