package org.adorno.learning;

import io.quarkus.test.junit.QuarkusTest;
import org.adorno.learning.config.GreetingConfig;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class ExampleResourceTest {

    @Inject
    private GreetingConfig greetingConfig;

    @Test
    public void testHelloEndpoint() {
        given()
                .when().get("/hello")
                .then()
                .statusCode(200)
                .header("Content-Type", "text/plain;charset=UTF-8")
                .body(is(String.format(
                        "hello %s %s %s",
                        greetingConfig.title,
                        greetingConfig.name.orElse("XXX"),
                        greetingConfig.suffix)));
    }

}