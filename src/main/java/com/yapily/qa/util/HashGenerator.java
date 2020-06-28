package com.yapily.qa.util;

import com.yapily.qa.base.BasePage;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;

public class HashGenerator extends BasePage {


    String public_key = getPublicKey();
    String private_key = getPrivateKey();
    String time_stamp;

    public static String md5Java(String message){

        String digest = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hash = md.digest(message.getBytes("UTF-8"));
            //converting byte array to Hexadecimal String
            StringBuilder sb = new StringBuilder(2*hash.length);
            for(byte b : hash){
                sb.append(String.format("%02x", b&0xff));
            }
            digest = sb.toString();
        } catch (UnsupportedEncodingException ex) {
        } catch (NoSuchAlgorithmException ex) {
        }
        return digest;
    }

    public String getStringToHash(){
        String hashString = time_stamp+private_key+public_key;
        return hashString;
    }

    public String getTimeStampGenerator() {

        Calendar calendar= Calendar.getInstance();
        String timeStamp = String.valueOf(calendar.getTimeInMillis());
        time_stamp = timeStamp;
        return timeStamp;
    }

}
