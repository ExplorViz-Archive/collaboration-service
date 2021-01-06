package net.explorviz.extension.vr;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class GreetingResourceTest {

    @Test
    public void testHelloEndpoint() {
        given().when().get("/C:/Program Files/Git/hello").then().statusCode(200).body(is("Hello RESTEasy"));
    }

}