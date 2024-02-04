package com.cs309.quoridorApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class QuoridorSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuoridorSpringApplication.class, args);
	}

//	@Bean
//	public Docket getAPIdocs() {
//		return new Docket(DocumentationType.SWAGGER_2)
//				.select()
//				.apis(RequestHandlerSelectors.any())
//				.paths(PathSelectors.any())
//				.build();
//	}

	/*
	@Bean(name="entityManagerFactory")
	public LocalSessionFactoryBean sessionFactory() {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();

		return sessionFactory;
	}
	 */
}

/*
{
    "login":false,
    "username":"name",
    "password":123,
    "name":"me"
}

"c8dd91ca-2fab-4b18-a555-7a97ba86e320"
id": "73baeab1-ac5a-4609-94b5-bc15b454f344


{
    "id": "7f1e0121-e59b-4814-a554-1a54e8c03dd7",
    "gameId":0,
    "x":3,
    "y":3
}
 */