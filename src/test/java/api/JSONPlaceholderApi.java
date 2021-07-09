package api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import model.Comments;
import model.Posts;
import net.serenitybdd.core.Serenity;
import net.thucydides.core.annotations.Step;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class JSONPlaceholderApi {

    final String baseUrl = "https://jsonplaceholder.typicode.com/";

    @Step
    public Response getUserByUserName(final String userName) {
        return RestAssured.given()
                .baseUri(baseUrl)
                .basePath("users")
                .queryParam("username", userName)
                .when().log().all()
                .get()
                .then()
                .statusCode(200)
                .extract().response();
    }

    @Step
    public Response getPostsWrittenByUser() {
        final String userId = Serenity.sessionVariableCalled("userId");
        return RestAssured.given()
                .baseUri(baseUrl)
                .basePath("posts")
                .queryParam("userId", userId)
                .when().log().all()
                .get()
                .then().extract().response();
    }

    @Step
    public Map<String, List<Comments>> getCommentsFromPost() {
        Map<String, List<Comments>> commentsMap = new HashMap<>();
        List<Posts> postList = Serenity.sessionVariableCalled("postList");
        for (Posts post : postList) {
            String postId = String.valueOf(post.getId());
            Comments[] commentsArray =
                    RestAssured.given()
                            .queryParam("postId", postId)
                            .when().log().all()
                            .get(baseUrl + "comments")
                            .then()
                            .statusCode(200)
                            .extract().as(Comments[].class);
            System.out.println("Size of Comments Array: \n" + commentsArray.length);
            commentsMap.put(postId, Arrays.asList(commentsArray));
        }
        return commentsMap;
    }

    public boolean validateEmailAddress(String emailAddress){
        Pattern validEmailRegex = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = validEmailRegex.matcher(emailAddress);
        return matcher.find();
    }
}
