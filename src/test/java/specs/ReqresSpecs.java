package specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.filter.log.LogDetail.BODY;
import static io.restassured.filter.log.LogDetail.STATUS;
import static io.restassured.http.ContentType.JSON;
import static io.restassured.RestAssured.with;

public class ReqresSpecs {

    public static RequestSpecification requestRegres = with()
            .filter(withCustomTemplates())
            .log().uri()
            .log().body()
            .log().headers()
            .contentType(JSON);

    public static RequestSpecification requestRegres415 = with()
            .filter(withCustomTemplates())
            .log().uri();


    public static ResponseSpecification responseCreateUserSpec = new ResponseSpecBuilder()
            .expectStatusCode(201)
            .log(STATUS)
            .log(BODY)
            .build();

    public static ResponseSpecification responseStatus200 = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .log(STATUS)
            .log(BODY)
            .build();

    public static ResponseSpecification responseStatus415 = new ResponseSpecBuilder()
            .expectStatusCode(415)
            .log(STATUS)
            .log(BODY)
            .build();

    public static ResponseSpecification responseStatus404 = new ResponseSpecBuilder()
            .expectStatusCode(404)
            .log(STATUS)
            .log(BODY)
            .build();

}
