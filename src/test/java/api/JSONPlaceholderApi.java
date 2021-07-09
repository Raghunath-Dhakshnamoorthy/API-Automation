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

public class JSONPlaceholderApi {

    final String baseUrl = "https://jsonplaceholder.typicode.com/";

    /**
     * Method to retrieve user response for given user name
     *
     * @param userName
     *         username
     * @return {@link Response} the users response
     */
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

    /**
     * Method to retrieve the posts written by an user
     *
     * @return {@link Response} the posts response
     */
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

    /**
     * Method to retrieve the comments for each post
     *
     * @return the map which contains comments in each post
     */
    @Step
    public Map<String, List<Comments>> getCommentsFromPost() {
        final Map<String, List<Comments>> commentsMap = new HashMap<>();
        final List<Posts> postList = Serenity.sessionVariableCalled("postList");
        //Iterate through each post and retrieve the Comments
        for (final Posts post : postList) {
            String postId = String.valueOf(post.getId());
            final Comments[] commentsArray =
                    RestAssured.given()
                            .queryParam("postId", postId)
                            .when().log().all()
                            //To Highlight: We can also give url in get()
                            .get(baseUrl + "comments")
                            .then()
                            .statusCode(200)
                            .extract().as(Comments[].class);
            //Add the Comments for each post to a map
            commentsMap.put(postId, Arrays.asList(commentsArray));
        }
        return commentsMap;
    }

    /**
     * Method to validate email address
     *
     * @param emailAddress
     *         the email address
     * @return {@link Boolean} true if the email is valid and false for invalid
     */
    public boolean validateEmailAddress(String emailAddress) {
        final Pattern validEmailRegex = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        final Matcher matcher = validEmailRegex.matcher(emailAddress);
        return matcher.find();
    }
}
