package com.testflow.apitest.cucumber.stepdefination;

import com.testflow.apitest.TestFlowManager;
import cucumber.api.java.en.Then;

import static com.testflow.apitest.cucumber.stepdefination.TestRunner.runner;

public class Verify {
    @Then("^I verify expected \"([^\"\"]*)\" actual \"([^\"\"]*)\"$")
    public void IVerifyDataToWith(String expected, String actual) {
        runner.verify(expected, actual);
    }
}
