package com.bokun.bkjcb.voteapp.NetWork;

import android.util.Log;

import com.bokun.bkjcb.voteapp.Model.HttpResult;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import org.ksoap2.serialization.SoapObject;

/**
 * Created by BKJCB on 2017/3/16.
 */

public class JsonParser {
    public static HttpResult parseSoap(SoapObject object) {
        HttpResult result = new HttpResult();
        if (object == null) {
            return result;
        }
        SoapObject detail = (SoapObject) object.getProperty(0);
        SoapManager manager = SoapManager.getInstance();
        String resultStr = manager.soapToJson(detail);
        Log.i("JsonParser",resultStr);
        com.google.gson.JsonParser parser = new com.google.gson.JsonParser();
        JsonElement element = parser.parse(resultStr).getAsJsonObject();
        Gson gson = new Gson();
        HttpResult httpResult = gson.fromJson(element, HttpResult.class);
        return httpResult;
    }
}
