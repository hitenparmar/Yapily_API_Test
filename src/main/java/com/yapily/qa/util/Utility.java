package com.yapily.qa.util;

import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Utility {

    public static String getValueByJPath(JSONObject responsejson, String jpath){
        Object obj = responsejson;
        for(String s : jpath.split("/"))
            if(!s.isEmpty())
                if(!(s.contains("[") || s.contains("]")))
                    obj = ((JSONObject) obj).get(s);
                else if(s.contains("[") || s.contains("]"))
                    obj = ((JSONArray) ((JSONObject) obj).get(s.split("\\[")[0])).get(Integer.parseInt(s.split("\\[")[1].replace("]", "")));
        return obj.toString();
    }

    public static Map<String, Object> jsonObjectToMap (JSONObject jsonObject) {
        Map<String, Object> map = new HashMap<String, Object>();
        Iterator<String> iterator = jsonObject.keySet().iterator();
        while(iterator.hasNext()) {
            String key = iterator.next();
            Object value = jsonObject.get(key);

            if(value instanceof JSONObject) {
                value = jsonObjectToMap((JSONObject) value);
            }
            map.put(key, value);
        }
        return map;
    }

    public static <T> T unMarshalling (CloseableHttpResponse response, Class<T> classObj) throws IOException {
        String jsonBody = EntityUtils.toString(response.getEntity(), "UTF-8");
        JSONObject jsonObject = new JSONObject(jsonBody);
        String resultBody =  Utility.getValueByJPath(jsonObject,"data/results");

        return new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(DeserializationFeature.UNWRAP_ROOT_VALUE, false)
                .readValue(resultBody, classObj);

    }

}
