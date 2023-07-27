package reqres.in.tests;

import org.junit.jupiter.api.Test;
import reqres.in.models.LoginBodyLombokModel;
import reqres.in.models.LoginBodyPojoModel;
import reqres.in.models.LoginResponsePojoModel;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApiTests extends TestBase {
    @Test
    void successfulLoginWithPojoTest() {
      //  String authData = "{ \"email\": \"eve.holt@reqres.in\", \"password\": \"cityslicka\" }"; // BAD PRACTICE
        LoginBodyPojoModel authData = new LoginBodyPojoModel();
        authData.setEmail("eve.holt@reqres.in");
        authData.setPassword("cityslicka");

        LoginResponsePojoModel loginResponse = given()
                .log().uri()
                .log().method()
                .log().body()
                .contentType(JSON)
                .body(authData)
                .when()
                .post("/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().as(LoginResponsePojoModel.class);

        assertEquals("QpwL5tke4Pnpja7X4", loginResponse.getToken());
                //.body("token", is("QpwL5tke4Pnpja7X4"));
    }
    @Test
    void successfulLoginWithLombokTest() {
        //  String authData = "{ \"email\": \"eve.holt@reqres.in\", \"password\": \"cityslicka\" }"; // BAD PRACTICE
        LoginBodyLombokModel authData = new LoginBodyLombokModel();
        authData.setEmail("eve.holt@reqres.in");
        authData.setPassword("cityslicka");

        LoginResponsePojoModel loginResponse = given()
                .log().uri()
                .log().method()
                .log().body()
                .contentType(JSON)
                .body(authData)
                .when()
                .post("/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().as(LoginResponsePojoModel.class);

        assertEquals("QpwL5tke4Pnpja7X4", loginResponse.getToken());
        //.body("token", is("QpwL5tke4Pnpja7X4"));
    }

    @Test
    void getListUsersTest() {
        given()
                .log().uri()
                .log().method()
                .log().body()
                .when()
                .get("/users?page=2")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("total", is(12));
    }

    @Test
    void getSingleUserTest() {
        given()
                .log().uri()
                .log().method()
                .log().body()
                .when()
                .get("/users/2")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("data.id", is(2))
                .body("data.email", is("janet.weaver@reqres.in"))
                .body("data.first_name", is("Janet"))
                .body("data.last_name", is("Weaver"));
    }

    @Test
    void postCreateUserTest() {
        String user = "{ \"name\": \"morpheus\",\n" +
                " \"job\": \"leader\" }"; // BAD PRACTICE
        given()
                .log().uri()
                .log().method()
                .log().body()
                .contentType(JSON)
                .body(user)
                .when()
                .post("/users")
                .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .body("name", is("morpheus"))
                .body("job", is("leader"));
    }

    @Test
    void unsuccessfulLoginTest() {
        String authData = "{\"email\": \"peter@klaven\"}";
        given()
                .log().uri()
                .log().method()
                .log().body()
                .contentType(JSON)
                .body(authData)
                .when()
                .post("/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Missing password"));
    }
}
