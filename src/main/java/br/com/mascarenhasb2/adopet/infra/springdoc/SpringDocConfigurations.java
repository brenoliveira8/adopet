package br.com.mascarenhasb2.adopet.infra.springdoc;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfigurations{
    @Bean
    public OpenAPI customOpenAPI(){
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearer-key",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")))
                .info(new Info()
                        .title("Adopet API")
                        .description("Esse projeto foi desenvolvido como parte do Challenge Back-end da Alura e tem como objetivo implementar o back-end para uma aplicação de adoção de pets, chamada ADOPET (empresa fictícia).")
                        .contact(new Contact()
                                .name("Breno Mascarenhas")
                                .email("brenoliveira8@gmail.com"))
                        .version("v1.0"));
    }
}
