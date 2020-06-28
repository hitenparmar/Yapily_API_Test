package com.yapily.qa.stepDef;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yapily.qa.base.BasePage;
import com.yapily.qa.client.ErrorMessageHandler;
import com.yapily.qa.client.RestApiClient;
import com.yapily.qa.pages.characters;
import com.yapily.qa.util.HashGenerator;
import com.yapily.qa.util.Utility;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.junit.Assert;

import java.io.IOException;

public class stepDefinition {

    RestApiClient restApiClient;
    CloseableHttpResponse response;
    BasePage basePageObj;
    HashGenerator hashGenerator;
    String url,apikey;
    String ts;
    String hash;
    characters character;
    String characterID;

    @Before
    public void setUp() {
        basePageObj = new BasePage();
        hashGenerator = new HashGenerator();
        url = basePageObj.prop.getProperty("URL")+basePageObj.prop.getProperty("characters");
        apikey = basePageObj.prop.getProperty("PUBLICKEY");
        ts = hashGenerator.getTimeStampGenerator();
        hash = HashGenerator.md5Java(hashGenerator.getStringToHash());
    }

    @After
    public void tearDown() throws IOException {
        response.close();
    }

    @Given("^As an authorized user i call get characters service$")
    public void as_an_authorized_user_i_call_get_characters_service() throws Throwable {

        restApiClient = new RestApiClient();
//        url = url+"?"+"limit=100&offset=100&ts="+ts+"&apikey="+apikey+"&hash="+hash;
        url = url+"?"+"ts="+ts+"&apikey="+apikey+"&hash="+hash;
        System.out.println("URL : "+url);
        response = restApiClient.get(url);

        int statusCode = response.getStatusLine().getStatusCode();
        System.out.println("StatusCode : "+statusCode);
        Assert.assertEquals(statusCode,200);

    }

    @Then("^I view all properties of the characters$")
    public void i_view_all_properties_of_the_characters() throws Throwable {
        String responseBody = EntityUtils.toString(response.getEntity(),"UTF-8");

        JSONObject jsonObject = new JSONObject(responseBody);

        String total = Utility.getValueByJPath(jsonObject,"/data/total");
        System.out.println("Total : "+total);

        String limit = Utility.getValueByJPath(jsonObject,"/data/limit");
        System.out.println("Limit : "+ limit);

        character = new characters();
//        character.getCharactersProperties(jsonObject,limit);
        character.getCharactersProperties(jsonObject);
    }

    @Given("^As an authorised user i call get characters all page service$")
    public void as_an_authorised_user_i_call_get_characters_all_page_service() throws Throwable {
        restApiClient = new RestApiClient();
    }

    @Then("^I iterate all pages characters$")
    public void i_iterate_all_pages_characters() throws Throwable {
        int j=100;

        for(int i = 0; i < 15; i++) {
            int offset = 100*i;
            url="";
            url=basePageObj.getCharactersBaseURL();
            String offSetString = String.valueOf(offset);
            url = url+"?"+"limit=100&offset="+offSetString+"&ts="+ts+"&apikey="+apikey+"&hash="+hash;

            System.out.println("************ PAGE NO : "+i+ " of Records : "+j);
            System.out.println("\nURL : "+url);

            response = restApiClient.get(url);
            int statusCode = response.getStatusLine().getStatusCode();

            System.out.println("\nStatusCode : "+statusCode);
            Assert.assertEquals(statusCode,200);

            character = new characters();
            character.iterateAllPagesCharacters(response);
        }
    }

    @Given("^As a user i set \"([^\"]*)\" parameters to call get characters service$")
    public void as_a_user_i_set_parameters_to_call_get_characters_service(String arg1) throws Throwable {
        restApiClient = new RestApiClient();

        if (arg1.equalsIgnoreCase("Missing apikey")){
            url = basePageObj.getCharactersBaseURL();
            url = url+"?"+"ts="+ts+"&hash="+hash;

            response = restApiClient.get(url);
        } else if(arg1.equalsIgnoreCase("Invalid Credentials")) {
            String invaildTS = "123456";
            url = url+"?"+"ts="+invaildTS+"&apikey="+apikey+"&hash="+hash;

            response = restApiClient.get(url);
        }
    }

    @Then("^I can see error message as \"([^\"]*)\" with \"([^\"]*)\"$")
    public void i_can_see_error_message_as_with(String errorMessage, String statusCode) throws Throwable {
        String responseCode = String.valueOf(response.getStatusLine().getStatusCode());

        System.out.println("Response Code : "+responseCode);
        String message = ErrorMessageHandler.getErrorMessage(response);

        if (errorMessage.equals(message)) {
            Assert.assertEquals(responseCode,statusCode);
        }
    }

    @Given("^As an authorised user i call get characters with \"([^\"]*)\"$")
    public void as_an_authorised_user_i_call_get_characters_with(String id) throws Throwable {
        characterID =id;
        restApiClient = new RestApiClient();
        url = url+"/"+id+"?ts="+ts+"&apikey="+apikey+"&hash="+hash;
        System.out.println("URL : "+url);
        response = restApiClient.get(url);
    }

    @Then("^I see \"([^\"]*)\" parameter \"([^\"]*)\" as \"([^\"]*)\"$")
    public void i_see_parameter_as(String properties, String parameter, String noOfAvailable) throws Throwable {
        character = new characters();
        String availableValue = character.getCharactersPropertiesByID(response, properties, parameter);
        Assert.assertEquals(noOfAvailable, availableValue);
    }
}
