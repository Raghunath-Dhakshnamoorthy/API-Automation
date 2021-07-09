package stepDefinition;

import api.JSONPlaceholderApi;
import com.google.gson.Gson;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import model.Comments;
import model.Posts;
import net.serenitybdd.core.Serenity;
import net.thucydides.core.annotations.Steps;
import org.jruby.RubyProcess;
import org.junit.Assert;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class StepDefinitions {
    @Steps
    JSONPlaceholderApi JSONPlaceholderApi;

    @When("I search for the user with username {string}")
    public void iSearchForTheUserWithUsername(final String userName) {
        Serenity.setSessionVariable("userResponse").to(JSONPlaceholderApi.getUserByUserName(userName));
    }

    @Then("I should retrieve the user details")
    public void iShouldRetrieveTheUserDetails() {
        final Response userResponse = Serenity.sessionVariableCalled("userResponse");
        final String userId = userResponse.then().extract().path("id").toString();
        Serenity.setSessionVariable("userId").to(userId.substring(1, userId.length()-1));
    }

    @When("I retrieve the posts written by the user")
    public void iRetrieveThePostsWrittenByTheUser() {
        Serenity.setSessionVariable("postResponse").to(JSONPlaceholderApi.getPostsWrittenByUser());
    }

    @Then("I should retrieve all the posts")
    public void iShouldRetrieveAllThePosts() {
        final Response postResponse = Serenity.sessionVariableCalled("postResponse");
        Assert.assertEquals("Search posts failed with status code", 200, postResponse.getStatusCode());
        List<Posts> postList = Arrays.asList(postResponse.then().extract().as(Posts[].class));
        System.out.println("Post list size: \n" + postList.size());
        Serenity.setSessionVariable("postList").to(postList);
    }

    @When("I retrieve the comments for each post")
    public void iRetrieveTheCommentsForEachPost() {
        Serenity.setSessionVariable("commentsMap").to(JSONPlaceholderApi.getCommentsFromPost());
    }

    @Then("I should validate emails in the comment are in proper format")
    public void iShouldValidateEmailsInTheCommentAreInProperFormat() {
        Map<String, List<Comments>> commentsMap = Serenity.sessionVariableCalled("commentsMap");
        List<String> emailList = commentsMap.entrySet().stream()
                .flatMap(entry -> entry.getValue().stream()
                        .map(Comments::getEmail)).collect(Collectors.toList());
        System.out.println("Email list size: \n" +emailList.size());
        for (String email: emailList) {
            boolean isEmailValid = JSONPlaceholderApi.validateEmailAddress(email);
            Assert.assertTrue("Email address is invalid: ", isEmailValid);
        }
    }
}
