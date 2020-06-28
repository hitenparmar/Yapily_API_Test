package com.yapily.qa.pages;

import com.yapily.qa.util.Utility;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class characters {

    String id;
    String description;
    String modified;
    String resourceURI;
    String thumbnail;
    String comics;
    String stories;
    String events;
    String urls;

    public characters() {}

    public characters(String id, String description, String modified, String resourceURI, String thumbnail, String comics, String stories, String events, String urls, String name) {
        this.id = id;
        this.description = description;
        this.modified = modified;
        this.resourceURI = resourceURI;
        this.thumbnail = thumbnail;
        this.comics = comics;
        this.stories = stories;
        this.events = events;
        this.urls = urls;
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public String getResourceURI() {
        return resourceURI;
    }

    public void setResourceURI(String resourceURI) {
        this.resourceURI = resourceURI;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getComics() {
        return comics;
    }

    public void setComics(String comics) {
        this.comics = comics;
    }

    public String getStories() {
        return stories;
    }

    public void setStories(String stories) {
        this.stories = stories;
    }

    public String getEvents() {
        return events;
    }

    public void setEvents(String events) {
        this.events = events;
    }

    public String getUrls() {
        return urls;
    }

    public void setUrls(String urls) {
        this.urls = urls;
    }

    public void getCharactersProperties(JSONObject jsonObject, String limit) {
        for(int i = 0; i < Integer.valueOf(limit); i++){
            System.out.println("\nRecord No : "+i);
            System.out.println();
            System.out.println("id : "+ Utility.getValueByJPath(jsonObject,"data/results["+i+"]/id"));
            System.out.println("name : "+Utility.getValueByJPath(jsonObject,"data/results["+i+"]/name"));
            System.out.println("description : "+Utility.getValueByJPath(jsonObject,"data/results["+i+"]/description"));
            System.out.println("modified : "+Utility.getValueByJPath(jsonObject,"data/results["+i+"]/modified"));
            System.out.println("resourceURI : "+Utility.getValueByJPath(jsonObject,"data/results["+i+"]/resourceURI"));
            System.out.println("thumbnail : "+Utility.getValueByJPath(jsonObject,"data/results["+i+"]/thumbnail"));
            System.out.println("comics : "+Utility.getValueByJPath(jsonObject,"data/results["+i+"]/comics"));
            System.out.println("stories : "+Utility.getValueByJPath(jsonObject,"data/results["+i+"]/stories"));
            System.out.println("events : "+Utility.getValueByJPath(jsonObject,"data/results["+i+"]/events"));
            System.out.println("urls : "+Utility.getValueByJPath(jsonObject,"data/results["+i+"]/urls"));
            System.out.println("--------**********---------");
        }
    }

    public void getCharactersProperties(JSONObject jsonObject) {
        int j=1;
        Map<String, Object> jsonResponseObject =  Utility.jsonObjectToMap(jsonObject);

        for(Map.Entry entry :  jsonResponseObject.entrySet()) {
            if(entry.getKey().toString().equals("data")) {
                HashMap<String, String> dataHashMap = (HashMap<String, String>) entry.getValue();

                for (Map.Entry resultData : dataHashMap.entrySet()) {
                    if (resultData.getKey().toString().equals("results")) {
                        JSONArray jsonArray = (JSONArray) resultData.getValue();

                        for(int i =0; i<jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            Map<String, Object> resultObject = Utility.jsonObjectToMap(jsonObject1);

                            System.out.println("RECORD NO : "+j);

                            for(Map.Entry resultIterate : resultObject.entrySet()) {
                                System.out.println("\n"+resultIterate.getKey()+ " :->  "+resultIterate.getValue());
                            }
                            System.out.println("\n******************************************\n");
                            j++;
                        }
                    }
                }
            }
        }
    }

    public void iterateAllPagesCharacters(CloseableHttpResponse response) throws IOException {
        String responseBody = EntityUtils.toString(response.getEntity(),"UTF-8");
        JSONObject jsonObject = new JSONObject(responseBody);
        Map<String, Object> jsonResponseObject =  Utility.jsonObjectToMap(jsonObject);
        int j=1;

        for(Map.Entry entry :  jsonResponseObject.entrySet()) {
            if(entry.getKey().toString().equals("data")) {
                HashMap<String, String> dataHashMap = (HashMap<String, String>) entry.getValue();

                for (Map.Entry resultData : dataHashMap.entrySet()) {
                    if (resultData.getKey().toString().equals("results")) {
                        JSONArray jsonArray = (JSONArray) resultData.getValue();

                        for(int i =0; i<jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            Map<String, Object> resultObject = Utility.jsonObjectToMap(jsonObject1);

                            System.out.println("\nRECORD NO : "+j);

                            for(Map.Entry resultIterate : resultObject.entrySet()) {
//                                System.out.println("\n"+resultIterate.getKey()+ " :->  "+resultIterate.getValue());
                                if(resultIterate.getKey().toString().equals("name")){
                                    System.out.println("\nCharacter "+resultIterate.getKey()+ " :->  "+resultIterate.getValue());
                                }
                            }
                            System.out.println("\n******************************************\n");
                            j++;
                        }
                    }
                }
            }
        }
    }

    public String getCharactersPropertiesByID(CloseableHttpResponse response, String properties, String parameter) throws IOException {
        String responseBody = EntityUtils.toString(response.getEntity(),"UTF-8");
        JSONObject jsonObject = new JSONObject(responseBody);
        String total = Utility.getValueByJPath(jsonObject,"/data/total");
        System.out.println("\n Total : "+total);
        String value = null;

        for(int i =0; i< Integer.valueOf(total); i++) {
           String comics = Utility.getValueByJPath(jsonObject,"data/results["+i+"]/"+properties);
           JSONObject jsonObject1 = new JSONObject(comics);
           value = Utility.getValueByJPath(jsonObject1,parameter);
           System.out.println("\n Available Value : "+value);
        }
        return value;
    }
}
