package org.esi.grable;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GrableApplication {

    public static void main(String[] args) {
        SpringApplication.run(GrableApplication.class, args);
    }

    @Bean
    public OpenAPI grableAPI() {
        return new OpenAPI()
                .info(new Info().title("Grable")
                        .description("Grable - API documentation"));
    }
}
