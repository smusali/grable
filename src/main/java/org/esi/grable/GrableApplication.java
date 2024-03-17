package org.esi.grable;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.boot.SpringApplication;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GrableApplication {

    public static void main(String[] args) {
        SpringApplication.run(GrableApplication.class, args);
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Grable API")
                        .description("Comprehensive API documentation for the Grable project"));
    }

    @Bean
    public GroupedOpenApi menuItemsApi() {
        return GroupedOpenApi.builder()
                .group("Menu Items")
                .packagesToScan("org.esi.grable.menuItems.controller")
                .build();
    }

    @Bean
    public GroupedOpenApi menusApi() {
        return GroupedOpenApi.builder()
                .group("Menus")
                .packagesToScan("org.esi.grable.menus.controller")
                .build();
    }

    @Bean
    public GroupedOpenApi restaurantsApi() {
        return GroupedOpenApi.builder()
                .group("Restaurants")
                .packagesToScan("org.esi.grable.restaurants.controller")
                .build();
    }
}
