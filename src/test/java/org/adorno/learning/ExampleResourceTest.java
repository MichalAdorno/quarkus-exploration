package org.adorno.learning;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.adorno.learning.config.GreetingConfig;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
@QuarkusTestResource(H2DatabaseTestResource.class)
public class ExampleResourceTest {

    @Inject
    GreetingConfig greetingConfig;

    @Test
    public void testHelloEndpoint() {
        given()
                .when().get("/application/hello")
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