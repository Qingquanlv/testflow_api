package com.testflow.apitest.cucumber.stepdefination;

import com.testflow.apitest.TestFlowManager;
import cucumber.api.java.en.Then;

import static com.testflow.apitest.cucumber.stepdefination.TestRunner.runner;

public class Verify {

    @Then("^I verify expected \"([^\"\"]*)\" actual \"([^\"\"]*)\"$")
    public void IVerifyDataToWith(String expected, String actual) {
        runner.verify(expected, actual);
    }

    @Then("^I verify object \"([^\"\"]*)\" key \"([^\"\"]*)\" value \"([^\"\"]*)\"$")
    public void IVerifyObjectKeyValue(String object, String key, String value) {
        runner.verify(object, key, value);
    }

    @Then("^I verify object type \"([^\"\"]*)\" expect key \"([^\"\"]*)\" actual value \"([^\"\"]*)\" pkMap \"([^\"\"]*)\" noComppareItemMap \"([^\"\"]*)\"$")
    public void IVerifyObjectKeyValue(String type, String expectKey, String actualKey, String pkMap, String noComppareItemMap) {
        runner.verify(type, expectKey, actualKey, pkMap, noComppareItemMap);
    }
}

