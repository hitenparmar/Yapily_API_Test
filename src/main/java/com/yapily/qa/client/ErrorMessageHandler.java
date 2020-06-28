package com.yapily.qa.client;

import com.yapily.qa.util.Utility;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;

public class ErrorMessageHandler {
    ErrorMessageHandler() {
    }

    public static String getErrorMessage(CloseableHttpResponse response) throws IOException {
        String responseBody = EntityUtils.toString(response.getEntity(),"UTF-8");
        JSONObject jsonObject = new JSONObject(responseBody);
        String message=null;

        Map<String, Object> jsonResponseObject =  Utility.jsonObjectToMap(jsonObject);
        for(Map.Entry entry : jsonResponseObject.entrySet()) {
            System.out.println(""+entry.getKey()+"  : "+entry.getValue());
            if (entry.getKey().equals("message"))
                message = entry.getValue().toString();
        }
        return message;
    }
}
