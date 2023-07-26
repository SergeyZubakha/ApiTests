package reqres.in;

import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;

public class StatusTests {
    @Test
    void checkTotal() {
        given()
                .when()
                .get("")
                .then()
                .body()
    }
} 
