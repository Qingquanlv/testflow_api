package com.testflow.apitest.cucumber.stepdefination;

import com.testflow.apitest.TestFlowManager;
import cucumber.api.DataTable;
import cucumber.api.java.en.When;

public class Request {

    @When("^I send request \"([^\"\"]*)\" to url \"([^\"\"]*)\" get \"([^\"\"]*)\" with$")
    public void ISendRequestToUrlWith(String requsetName, String url, String responceName, DataTable table) {
        TestFlowManager.runner().sendRequest(requsetName, table.asMaps(String.class, String.class), url, responceName);
    }

    @When("^I send JSON request \"([^\"\"]*)\" to url \"([^\"\"]*)\" get \"([^\"\"]*)\"$")
    public void IBuildRequestToUrl(String requsetName, String url, String responceName) {
        TestFlowManager.runner().sendRequest(requsetName, url, responceName);
    }
}
