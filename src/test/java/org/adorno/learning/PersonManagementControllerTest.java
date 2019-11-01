package org.adorno.learning;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
@QuarkusTestResource(H2DatabaseTestResource.class)
public class PersonManagementControllerTest {

    @Test
    public void testPersonCreateServiceValidation_whenValidPayload() {
        given()
                .body("{\"name\": \"Yan Lianke\", \"status\": \"ALIVE\", \"birth\":\"1958-08-24\"}")
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
            .when()
                .post("/application/personmgmt/create")
            .then()
                .statusCode(202);
    }

    @Test
    public void testPersonCreateServiceValidation_whenNotValidPayload_1() {
        given()
                .body("{\"name\": \"Yan Lianke\", \"birth\":\"1958-08-24\"}")
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
            .when()
                .post("/application/personmgmt/create")
            .then()
                .statusCode(500);
    }

    @Test
    public void testPersonCreateServiceValidation_whenNotValidPayload_2() {
        given()
                .body("{\"name\": \"\", \"status\": \"ALIVE\", \"birth\":\"1958-08-24\"}")
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
            .when()
                .post("/application/personmgmt/create")
            .then()
                .statusCode(500);
    }
}
