package com.testflow.apitest.cucumber.stepdefination;

import com.testflow.apitest.TestFlowManager;
import cucumber.api.java.en.When;

public class Parser {

    @When("^I parse data from via file \"([^\"\"]*)\" method \"([^\"\"]*)\" from \"([^\"\"]*)\" type \"([^\"\"]*)\" to \"([^\"\"]*)\"$")
    public void ICovertDataToWith(String convertFileName, String convertMethod, String sourceKey, String sourceType, String targetKey) {
        TestFlowManager.runner().reflectParse(convertFileName, convertMethod, sourceKey, sourceType, targetKey);
    }
}
