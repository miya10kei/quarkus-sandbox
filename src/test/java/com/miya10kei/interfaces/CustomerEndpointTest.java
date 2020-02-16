package com.miya10kei.interfaces;

import io.quarkus.test.junit.QuarkusTest;
import javax.json.Json;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;

@QuarkusTest
class CustomerEndpointTest {

  @Test
  void testCustomerService() {
    // Test GET
    given()
        .when()
        .get("/customers")
        .then()
        .statusCode(Status.OK.getStatusCode())
        .body("$.size()", is(2));

    // Create a JSON Object for the Order
    var objOrder = Json.createObjectBuilder().add("item", "bike").add("price", 100L).build();

    // Test POST Order for Customer #1
    given()
        .contentType(MediaType.APPLICATION_JSON)
        .body(objOrder.toString())
        .when()
        .post("/orders/1")
        .then()
        .statusCode(Status.NO_CONTENT.getStatusCode());

    // Create new JSON for Order #1
    objOrder =
        Json.createObjectBuilder()
            .add("id", 1L)
            .add("item", "mountain bike")
            .add("price", 100L)
            .build();

    // Test UPDATE Order #1
    given()
        .contentType(MediaType.APPLICATION_JSON)
        .body(objOrder.toString())
        .when()
        .put("/orders")
        .then()
        .statusCode(Status.NO_CONTENT.getStatusCode());

    // Test GET for Order #1
    given()
        .when()
        .get("/orders?customerId=1")
        .then()
        .statusCode(Status.OK.getStatusCode())
        .body(containsString("mountain bike"));

    // Test DELETE Order #1
    given().when().delete("/orders/1").then().statusCode(Status.NO_CONTENT.getStatusCode());
  }
}
