package stepDefinition;

import api.JSONPlaceholderApi;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import model.Comments;
import model.Posts;
import net.serenitybdd.core.Serenity;
import net.thucydides.core.annotations.Steps;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
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
        Serenity.setSessionVariable("userId").to(userId.substring(1, userId.length() - 1));
    }

    @When("I retrieve the posts written by the user")
    public void iRetrieveThePostsWrittenByTheUser() {
        final String userId = Serenity.sessionVariableCalled("userId");
        Serenity.setSessionVariable("postResponse").to(JSONPlaceholderApi.getPostsWrittenByUser(userId));
    }

    @Then("I should retrieve all the posts")
    public void iShouldRetrieveAllThePosts() {
        final Response postResponse = Serenity.sessionVariableCalled("postResponse");
        final List<Posts> postList = Arrays.asList(postResponse.then().extract().as(Posts[].class));
        Serenity.setSessionVariable("postList").to(postList);
    }

    @When("I retrieve the comments for each post")
    public void iRetrieveTheCommentsForEachPost() {
        Serenity.setSessionVariable("commentsMap").to(JSONPlaceholderApi.getAllCommentsForEachPost());
    }

    @Then("I should validate emails in the comment are in proper format")
    public void iShouldValidateEmailsInTheCommentAreInProperFormat() {
        final Map<String, List<Comments>> commentsMap = Serenity.sessionVariableCalled("commentsMap");
        //Retrieve all emailIds to a single list
        final List<String> emailList = commentsMap.entrySet().stream()
                .flatMap(entry -> entry.getValue().stream()
                        .map(Comments::getEmail)).collect(Collectors.toList());
        final List<String> invalidEmailsList = new ArrayList<>();
        boolean validEmail = true;
        for (String email : emailList) {
            boolean isEmailValid = JSONPlaceholderApi.validateEmailAddress(email);
            if (!isEmailValid) {
                invalidEmailsList.add(email);
                validEmail = false;
            }
        }
        Assert.assertTrue("Below email addresses are not invalid: \n" + invalidEmailsList.toString(), validEmail);
    }

    @When("an user tries to retrieve the {string} which is not available")
    public void anUserTriesToRetrieveTheWhichIsNotAvailable(final String invalidResource) {
        Serenity.setSessionVariable("invalidResourceResponse").to(JSONPlaceholderApi.getInvalidResourceResponse(invalidResource));
    }

    @Then("resource unavailable error should be thrown")
    public void resourceUnavailableErrorShouldBeThrown() {
        final Response invalidResourceResponse = Serenity.sessionVariableCalled("invalidResourceResponse");
        Assert.assertEquals("Resource unavailable error was not thrown", 404, invalidResourceResponse.statusCode());
    }
}
