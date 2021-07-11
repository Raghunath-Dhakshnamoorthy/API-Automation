# JSONPlaceholder API Automation
## Introduction

This test automation solution covers the following test scenario.

### Test Scenarios (as per the requirement)

1. Search for the user with username “Delphine”.
2. Use the details fetched to make a search for the posts written by the
user.
3. For each post, fetch the comments and validate if the emails in the
comment section are in the proper format.
4. Validate API behavior when unavailable/invalid resource is accessed
5. Resource creation - unique behavior of this application - `resource will not be really updated on the server but it will be faked as if`

#### Considerations

As mentioned in scenarios, only **users**, **posts** and **comments** resources of JSONPlaceholder API alone considered for this automation

### Framework Implementation

The components of this framework are Java, RestAssured, Serenity, Cucumber, JUnit & Maven.

## Project structure

```
src
  + test
    + java
      + api                       Package consisting of all actions in JSONPlaceholder API
          JSONPlaceholderApi      API calls/user actions (Serenity steps) for Users, Posts and Comments resources
      + model                     Domain model package for generating json response
          Comments                Contains data model for Comments json object
          Posts                   Contains data model for Posts json response
      + stepDefinition            Package contains step definitions
          StepDefinitions         Step definitions for corresponding test scenarios
      + TestRunner                Runner class to execute/invoke feature files
    + resources
      + features                  Package contains feature files
          JSONPlaceholder.feature Contains all scenarios for the test flows
      pom.xml                     Maven dependency management
      serenity.properties         Property file for defining serenity behavior
  + .circleci
        config.yml                Circle CI configuration file for execution and report generation in Circle CI
```
## Test Execution

Test execution can be done in 2 ways - Local and CircleCI

### Executing the tests in CircleCI

In CircleCI, we can execute the test cases manually by re-running the workflow in https://app.circleci.com/pipelines/github/Raghunath-Dhakshnamoorthy/JSONPlaceholder-API-Automation

#### CircleCI UI report

Once the pipeline is executed, we can access the html report from the pipeline artifacts `Pipeline -> Job(test) -> Artifacts`

#### Job scheduled in CircleCI

Additionally, I have scheduled this job in CircleCI pipeline to execute the test cases on 1st day of every month.

### Executing the tests locally

Clone the repository from GitHub using https://github.com/Raghunath-Dhakshnamoorthy/JSONPlaceholder-API-Automation.git to download the project in any IDE.
 
Then, run `mvn clean install` from the command line to execute all the feature files.

Once the test execution is completed, the test report will be available in `target/site/serenity/index.html`

#### Keyword - To Highlight

I have used the keyword **To Highlight** in many comments which was intended to highlight my different opinions/views in approaching solution
