package api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import net.serenitybdd.core.Serenity;
import net.thucydides.core.annotations.Step;

@Slf4j
public class JSONPlaceholderApi {

    final String baseUrl = "https://jsonplaceholder.typicode.com/";

    @Step
    public Response getUserByUserName(final String userName){
        return RestAssured.given()
                .baseUri(baseUrl)
                .basePath("users")
                .queryParam("username", userName)
                .when().log().all()
                .get()
                .then().extract().response();
    }

    @Step
    public Response getPostsWrittenByUser(){
        final String userId = Serenity.sessionVariableCalled("userId");
        return RestAssured.given()
                .baseUri(baseUrl)
                .basePath("posts")
                .queryParam("userId", userId)
                .when().log().all()
                .get()
                .then().extract().response();
    }
}
