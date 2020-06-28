package com.yapily.qa.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class BasePage {

    public Properties prop;

    public BasePage() {
        try {
            prop = new Properties();
            FileInputStream ip = new FileInputStream(System.getProperty("user.dir")+"/src/main/java/com/yapily/qa/config/config.properties");
            prop.load(ip);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String getCharactersBaseURL() {
        String url;
        url = prop.getProperty("URL")+prop.getProperty("characters");
        return url;
    }

    public String getPublicKey() {
        String publicKey = prop.getProperty("PUBLICKEY");
        return publicKey;
    }

    public String getPrivateKey() {
        String privateKey = prop.getProperty("PRIVATEKEY");
        return privateKey;
    }
}
