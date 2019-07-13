package com.testflow.apitest.cucumber.stepdefination;

import com.testflow.apitest.TestFlowManager;
import com.testflow.apitest.business.BufferManager;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.Scenario;
import org.junit.runner.RunWith;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;


@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/java/resources/", glue = "StepDefinitons",
        tags = { "@" }, format = {"pretty", "html:target/cucumber", "json:target/cucumber.json" })

public class TestRunner {

    @Before
    public void beforeScenario(final Scenario scenario) {
        System.out.println(String.format("Start test the Scenario: %s.\n", scenario.getName()));
    }

    @After
    public void afterScenario(final Scenario scenario) {
        System.out.println(String.format("Test the Scenario: %s end.\n", scenario.getName()));
    }
}

