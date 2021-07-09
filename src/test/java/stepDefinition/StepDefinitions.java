package stepDefinition;

import api.JSONPlaceholderApi;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import net.serenitybdd.core.Serenity;
import net.thucydides.core.annotations.Steps;
import org.junit.Assert;

public class StepDefinitions {
    @Steps
    JSONPlaceholderApi JSONPlaceholderApi;

    @When("I search for the user with username {string}")
    public void iSearchForTheUserWithUsername(final String userName) {
        final Response response = JSONPlaceholderApi.getUserByUserName(userName);
        Serenity.setSessionVariable("userResponse").to(response);
    }

    @Then("I should retrieve the user details")
    public void iShouldRetrieveTheUserDetails() {
        final Response userResponse = Serenity.sessionVariableCalled("userResponse");
        Assert.assertEquals("Search user failed with status code", 200, userResponse.getStatusCode());
        final String userId = userResponse.then().extract().path("id").toString();
        Serenity.setSessionVariable("userId").to(userId);
    }

    @When("I retrieve the posts written by the user")
    public void iRetrieveThePostsWrittenByTheUser() {
        final Response response = JSONPlaceholderApi.getPostsWrittenByUser();
        Serenity.setSessionVariable("postsResponse").to(response);
    }

    @Then("I should retrieve all the posts")
    public void iShouldRetrieveAllThePosts() {
        final Response postsResponse = Serenity.sessionVariableCalled("postsResponse");
        Assert.assertEquals("Search posts failed with status code", 200, postsResponse.getStatusCode());
        final String userId = postsResponse.then().extract().path("id").toString();
        Serenity.setSessionVariable("userId").to(userId);
    }

    @When("I retrieve the comments for each post")
    public void iRetrieveTheCommentsForEachPost() {
    }

    @Then("I should retrieve the comments")
    public void iShouldRetrieveTheComments() {
    }

    @And("I validate emails in the comment are in proper format")
    public void iValidateEmailsInTheCommentAreInProperFormat() {
    }
}
