package reqres.in.tests;

import org.junit.jupiter.api.BeforeAll;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.basePath;
public class TestBase {
    @BeforeAll
    public static void baseURL() {
        baseURI = "https://reqres.in";
        basePath = "/api";
    }
}

