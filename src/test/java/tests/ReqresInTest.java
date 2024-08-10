package tests;


import models.CreateUserBodyModel;
import models.CreateUserResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static specs.ReqresSpecs.*;


public class ReqresInTest extends TestBase {

    @Test
    void listDataNotEmptyTest() {
        step("запрос на получение данных", () ->
                given(requestRegres)
                        .get("/unknown")
                        .then()
                        .spec(responseStatus200)
                        .body("data", is(notNullValue())));
    }


    @Test
    void listDataIdTest() {

        step("запрос данных", () ->
                given(requestRegres)
                    .get("/unknown")
                    .then()
                    .spec(responseStatus200))
                    .body("data[0].id", is(1));
    }


    @Test
    void unSuccessfulCreate415Test() {
        step("не полный запрос", () ->
                given(requestRegres415)
                        .post("/users")
                        .then()
                        .spec(responseStatus415));
    }

    @Test
    @DisplayName("создание пользователя")
    void successfulCreateUserTest() {

        CreateUserBodyModel authData = new CreateUserBodyModel();
        authData.setName("morpheus");
        authData.setJob("leader");

        CreateUserResponseModel response =
                step("отправка запроса на создание пользователя", () ->
                        given(requestRegres)
                                .body(authData)
                                .when()
                                .post("/users")
                                .then()
                                .spec(responseCreateUserSpec)
                                .extract().as(CreateUserResponseModel.class));
        step("проверка имени пользователя", () ->
                assertEquals("morpheus", response.getName()));
        step("проверка должности пользователя", () ->
                assertEquals("leader", response.getJob()));
    }


    @Test
    void singleUserNotFound() {
        step("запрос на не зарегистрированного пользователя", () ->
                given(requestRegres)
                        .get("/users/23")
                        .then()
                        .spec(responseStatus404));
    }


}