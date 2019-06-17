package com.testflow.apitest.cucumber.stepdefination;

import com.testflow.apitest.TestFlowManager;
import cucumber.api.java.en.Then;

public class Verify {
    @Then("^I verify expected \"([^\"\"]*)\" actual \"([^\"\"]*)\"$")
    public void IVerifyDataToWith(String expected, String actual) {
        TestFlowManager.verify(expected, actual);
    }
}
