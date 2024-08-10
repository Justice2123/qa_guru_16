package tests;


import models.CreateUserBodyModel;
import models.CreateUserResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;


import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static specs.ReqresSpecs.*;

@Tag("allTests")
public class ReqresInTest extends TestBase {

    @Test
    @DisplayName("проверка массива на null")
    @Tag("regression")
    void listDataNotEmptyTest() {
        step("запрос на получение данных", () ->
                given(requestRegres)
                        .get("/unknown")
                        .then()
                        .spec(responseStatus200)
                        .body("data", is(notNullValue())));
    }


    @Test
    @DisplayName("проверка значения в массиве")
    @Tag("smoke")
    void listDataIdTest() {

        step("запрос данных", () ->
                given(requestRegres)
                    .get("/unknown")
                    .then()
                    .spec(responseStatus200))
                    .body("data[0].id", is(1));
    }


    @Test
    @DisplayName("негативный тест для проверки 415 статуса")
    @Tag("regression")
    void unSuccessfulCreate415Test() {
        step("не полный запрос", () ->
                given(requestRegres415)
                        .post("/users")
                        .then()
                        .spec(responseStatus415));
    }

    @Test
    @DisplayName("создание пользователя")
    @Tag("regression")
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
                assertThat(response.getName()).isEqualTo("morpheus"));
        step("проверка должности пользователя", () ->
                assertThat(response.getJob()).isEqualTo("leader"));
    }


    @Test
    @DisplayName("негативный текст получение данных пользователя")
    @Tag("smoke")
    void singleUserNotFound() {
        step("запрос на не зарегистрированного пользователя", () ->
                given(requestRegres)
                        .get("/users/23")
                        .then()
                        .spec(responseStatus404));
    }


}