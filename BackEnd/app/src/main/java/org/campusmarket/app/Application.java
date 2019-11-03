package org.campusmarket.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
/**
 * A class that has a main method to run the spring application 
 * @author fadelalshammasi
 * @author nheisler
 *
 */
@EnableJpaRepositories(basePackages = "org.campusmarket.db.repositories")
// @ComponentScan(basePackages = "endpoints")
@SpringBootApplication
public class Application
{
	public static void main(String[] args) 
	{
		SpringApplication.run(Application.class, args);
	}
}