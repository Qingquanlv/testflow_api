package com.testflow.apitest.cucumber.stepdefination;

import com.testflow.apitest.TestFlowManager;
import cucumber.api.java.en.When;

public class Parser {
    @When("^I covert data from via file \"([^\"\"]*)\" soure \"([^\"\"]*)\" method \"([^\"\"]*)\" from data \"([^\"\"]*)\" to \"([^\"\"]*)\"$")
    public void ICovertDataToWith(String convertFileName, String convertFileSource, String convertMethodName, String sourceData, String sourceDataParamType, String targetDataParamType) {
        TestFlowManager.covert(convertFileName, convertFileSource, convertMethodName, sourceData, sourceDataParamType, sourceDataParamType);
    }
}
