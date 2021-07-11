package api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
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
     *         the user name
     * @return {@link ValidatableResponse} the users response
     */
    @Step
    public ValidatableResponse getUserByUserName(final String userName) {
        return RestAssured.given()
                .baseUri(baseUrl)
                .basePath("users")
                .queryParam("username", userName)
                .when().log().all()
                .get()
                .then()
                .statusCode(200);
    }

    /**
     * Method to retrieve the posts written by an user
     *
     * @param userId
     *         the user id
     * @return {@link ValidatableResponse} the posts response
     */
    @Step
    public ValidatableResponse getPostsWrittenByUser(final String userId) {
        return RestAssured.given()
                .baseUri(baseUrl)
                .basePath("posts")
                .queryParam("userId", userId)
                .when().log().all()
                .get()
                .then()
                .statusCode(200);
    }

    /**
     * Method to retrieve the comments for a post
     *
     * @param postId
     *         the post id
     * @return {@link ValidatableResponse} the posts response
     */
    @Step
    public ValidatableResponse getCommentsFromPost(final String postId) {
        return RestAssured.given()
                .baseUri(baseUrl)
                .basePath("comments")
                .queryParam("postId", postId)
                .when().log().all()
                .get()
                .then()
                .statusCode(200);
    }

    /**
     * Method to retrieve the comments for each post
     *
     * @return the map which contains comments in each post
     */
    @Step
    public Map<String, List<Comments>> getAllCommentsForEachPost() {
        final Map<String, List<Comments>> commentsMap = new HashMap<>();
        final List<Posts> postList = Serenity.sessionVariableCalled("postList");
        //Iterate through each post and retrieve the Comments
        for (final Posts post : postList) {
            final String postId = String.valueOf(post.getId());
            final Comments[] commentsArray = getCommentsFromPost(postId)
                    .extract().as(Comments[].class);
            //Add the Comments for each post to a map
            commentsMap.put(postId, Arrays.asList(commentsArray));
        }
        return commentsMap;
    }

    /**
     * Method to retrieve response when invalid resource name is given
     *
     * @param invalidResource
     *         the invalid resource name
     * @return {@link ValidatableResponse} the response
     */
    @Step
    public ValidatableResponse getInvalidResourceResponse(final String invalidResource) {
        return RestAssured.given()
                .baseUri(baseUrl)
                .basePath(invalidResource)
                .when().log().all()
                .get()
                .then();
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

    /**
     * Method to create new post
     *
     * @return {@link ValidatableResponse} the posts response
     */
    @Step
    public ValidatableResponse createNewPost() {
        return RestAssured.given()
                .baseUri(baseUrl)
                .basePath("posts")
                .header("Content-Type", "application/json; charset=UTF-8")
                .body(setPostsData())
                .when().log().all()
                .post()
                .then()
                .statusCode(201);
    }

    /**
     * Method to set data(json body) for create new post
     *
     * @return the json body data
     */
    private String setPostsData() {
        final Posts posts = new Posts(1, 1, "Fake", "Response");
        final Gson gsonBuilder = new GsonBuilder().create();
        return gsonBuilder.toJson(posts);
    }

    /**
     * Method to get post data by post id
     *
     * @param postId
     *         the post id
     * @return {@link ValidatableResponse} the posts response
     */
    @Step
    public ValidatableResponse getPostByPostId(final String postId) {
        return RestAssured.given()
                .when().log().all()
                //To Highlight: We can also give url as String in get()
                .get(baseUrl + "posts" + "/" + postId)
                .then();
        //To Highlight: Didn't perform status code validation here to make it a generic method
    }
}
