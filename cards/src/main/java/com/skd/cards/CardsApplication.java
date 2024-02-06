package com.skd.cards;

import com.skd.cards.dto.CardsContactDto;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@EnableConfigurationProperties(value = {CardsContactDto.class})
@OpenAPIDefinition(
		info = @Info(title = "Cards service Rest API documentation",
				description = "SKD Bank Cards service Rest API documentation",
				version = "v1",
				contact = @Contact(
						name = "Sumeet Dwivedi",
						email = "sumeet@skdbank.com",
						url = "https://github.com/motivated-coder"
				),
				license = @License(
						name = "Apache 2.0",
						url = "https://github.com/motivated-coder"
				)
		),
		externalDocs = @ExternalDocumentation(
				description =  "SKD Bank Cards microservice REST API Documentation",
				url = "https://www.eazybytes.com/swagger-ui.html"
		)

)
public class CardsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CardsApplication.class, args);
	}

}
