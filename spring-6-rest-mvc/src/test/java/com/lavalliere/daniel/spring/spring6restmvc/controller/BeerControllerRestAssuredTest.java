package com.lavalliere.daniel.spring.spring6restmvc.controller;


import com.atlassian.oai.validator.OpenApiInteractionValidator;
import com.atlassian.oai.validator.restassured.OpenApiValidationFilter;
import com.atlassian.oai.validator.whitelist.ValidationErrorsWhitelist;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springdoc.core.filters.OpenApiMethodFilter;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.atlassian.oai.validator.whitelist.rule.WhitelistRules.messageHasKey;
import static io.restassured.RestAssured.given;

// TO DISABLE SECURITY FILTERING DURING TEST ===========================================================================================
// @ActiveProfiles("test") // ----------> The SpringSecConfig defining the filerchain to implement security authentication/autorization
                           //             Would that the class annotation   @Profile("!test")    ie: All profiles BUT test
// @Import(BeerControllerRestAssuredTest.TestConfig.class)
// @ComponentScan(basePackages = "com.lavalliere.daniel.spring.spring6restmvc")
// =====================================================================================================================================
@SpringBootTest(
    // Specify that the SpringBootTest is a web application
    // since we want RestAssured to go against the running application server
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public class BeerControllerRestAssuredTest {

    // TO DISABLE SECURITY FILTERING DURING TEST =======================================================================================
    // @Configuration
    // public static class TestConfig {
    //    @Bean
    //    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
    //        http.authorizeHttpRequests(authorize -> {
    //            authorize.anyRequest().permitAll();
    //        });
    //        return http.build();
    //    }
    // }
    // =================================================================================================================================

    OpenApiValidationFilter filter = new OpenApiValidationFilter(
        OpenApiInteractionValidator
            .createForSpecificationUrl("openapi.yml")
            .withWhitelist(
                // Defined some errors to ignore
                // could also update/define as per the expected date format
                ValidationErrorsWhitelist.create()
                    .withRule(
                        "Ignore date format",
                        messageHasKey("validation.response.body.schema.format.date-time")
                    )
            )
        .build());

    // Determine the port that was assigned
    @LocalServerPort
    Integer localPort;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = localPort;
    }

    @Test
    void testListBeers() {
        given().contentType(ContentType.JSON)
            .when()
            .filter(filter)
            .get("/api/v1/beer")
            .then()
            .assertThat().statusCode(200);
    }
}