package com.yapily.qa;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(strict = true,
        features = {"src/main/resources/Features/"},
        glue = {"com.yapily.qa.stepDef"},
        tags = {"@Test1_a"},
        plugin = {"pretty", "json:target/cucumber.json","html:target/cucumber-html","html:target/cucumber-reports"}
)

public class RunTest {
}