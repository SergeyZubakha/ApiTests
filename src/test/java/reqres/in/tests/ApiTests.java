package reqres.in.tests;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import reqres.in.models.*;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static reqres.in.specs.Specifications.*;

public class ApiTests extends TestBase {

    @Test
    @Tag("ApiTests")
    void postSuccessfulLoginTest() {

        LoginBodyLombokModel authData = new LoginBodyLombokModel();
        authData.setEmail("eve.holt@reqres.in");
        authData.setPassword("cityslicka");

        LoginResponseLombokModel loginResponse = step("Make request", () ->
                given(loginRequestSpec)
                        .body(authData)
                        .when()
                        .post("/login")
                        .then()
                        .spec(loginResponseSpec)
                        .extract().as(LoginResponseLombokModel.class));

        step("Check response", () ->
                assertEquals("QpwL5tke4Pnpja7X4", loginResponse.getToken()));

    }

    @Test
    @Tag("ApiTests")
    void getListUsersTest() {

        GetListUsersResponseModel listUsers = step("Make request", () ->
                given(getUsersRequestSpec)
                        .when()
                        .get("/users?page=2")
                        .then()
                        .spec(getUsersResponseSpec)
                        .extract().as(GetListUsersResponseModel.class));

        step("Check response", () -> {
            assertEquals(12, listUsers.getTotal());
            assertEquals("https://reqres.in/#support-heading", listUsers.getSupport().getUrl());
            assertEquals(7, listUsers.getData().get(0).getId());
        });

    }

    @Test
    @Tag("ApiTests")
    void getSingleUserTest() {

        GetUser2ResponseModel user2 = step("Make request", () ->
                given(getUser2RequestSpec)
                        .when()
                        .get("/users/2")
                        .then()
                        .spec(getUser2ResponseSpec)
                        .extract().as(GetUser2ResponseModel.class));

        step("Check response", () -> {
            assertEquals(2, user2.getData().getId());
            assertEquals("janet.weaver@reqres.in", user2.getData().getEmail());
            assertEquals("Janet", user2.getData().getFirst_name());
            assertEquals("Weaver", user2.getData().getLast_name());
            assertEquals("https://reqres.in/img/faces/2-image.jpg", user2.getData().getAvatar());
            assertEquals("https://reqres.in/#support-heading", user2.getSupport().getUrl());
            assertEquals("To keep ReqRes free, contributions towards server costs are appreciated!", user2.getSupport().getText());
        });

    }

    @Test
    @Tag("ApiTests")
    void postCreateUserTest() {
        PostCreateBodyModel newUser = new PostCreateBodyModel();
        newUser.setName("morpheus");
        newUser.setJob("leader");

        PostCreateResponseModel postCreateResponseModel = step("Make request", () ->

                given(postCreateRequestSpec)
                        .body(newUser)
                        .when()
                        .post("/users")
                        .then()
                        .spec(postCreateResponseSpec)
                        .extract().as(PostCreateResponseModel.class));

        step("Check response", () -> {
                    assertEquals("morpheus", postCreateResponseModel.getName());
                    assertEquals("leader", postCreateResponseModel.getJob());
                });

    }

    @Test
    @Tag("ApiTests")
    void postUnsuccessfulLoginTest() {

        PostUnsuccessfulLoginBodyModel postUnsuccessfulLoginBodyModel = new PostUnsuccessfulLoginBodyModel();
        postUnsuccessfulLoginBodyModel.setEmail("peter@klaven");

        PostUnsuccessfulLoginResponseModel postUnsuccessfulLoginResponseModel = step("Make request", () ->
                given(postUnsuccessfulLoginRequestSpec)
                        .body(postUnsuccessfulLoginBodyModel)
                        .when()
                        .post("/login")
                        .then()
                        .spec(postUnsuccessfulLoginResponseSpec)
                        .extract().as(PostUnsuccessfulLoginResponseModel.class));

        step("Check response", () ->
                assertEquals("Missing password", postUnsuccessfulLoginResponseModel.getError()));
    }

}
