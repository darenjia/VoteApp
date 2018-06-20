package com.bokun.bkjcb.voteapp.NetWork;

import android.util.Log;

import com.bokun.bkjcb.voteapp.Model.HttpResult;
import com.bokun.bkjcb.voteapp.Model.Match;
import com.bokun.bkjcb.voteapp.Model.PersonInfo;
import com.bokun.bkjcb.voteapp.Model.PersonResult;
import com.bokun.bkjcb.voteapp.Model.VersionInfo;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;
import java.util.List;

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
        Log.i("JsonParser", resultStr);
        com.google.gson.JsonParser parser = new com.google.gson.JsonParser();
        JsonElement element = parser.parse(resultStr).getAsJsonObject();
        Gson gson = new Gson();
        HttpResult httpResult = gson.fromJson(element, HttpResult.class);
        return httpResult;
    }


    public static HttpResult getData(SoapObject object) {
        SoapObject soapObject = (SoapObject) object.getProperty(0);//.getProperty("GetActivityResponse");//GetActivityResult
        //  SoapObject soapObject = (SoapObject) soapObject1.getProperty("GetActivityResponse");
        String success = soapObject.getPropertyAsString("success");
        String message = soapObject.getPropertyAsString("message");
        String data;
        try {
            data = soapObject.getPropertyAsString("data");
        } catch (Exception e) {
            data = "";
        }
        HttpResult jsonResult = new HttpResult();
        jsonResult.setSuccess(Boolean.parseBoolean(success));
        jsonResult.setData(data);
        jsonResult.setMessage(message);
//        XLog.i(success + message + data);

        return jsonResult;
    }

    public static Match getList(String str) {
        com.google.gson.JsonParser parser = new com.google.gson.JsonParser();
        JsonElement element = parser.parse(str).getAsJsonObject();
        Gson gson = new Gson();
        Match match = gson.fromJson(element, Match.class);
        return match;
    }

    public static PersonInfo getPerson(String str) {
        com.google.gson.JsonParser parser = new com.google.gson.JsonParser();
        JsonElement element = parser.parse(str).getAsJsonObject();
        Gson gson = new Gson();
        PersonInfo personInfo = gson.fromJson(element, PersonInfo.class);
        return personInfo;
    }

    public static List<PersonResult> getPersonResult(String str) {
        List<PersonResult> persons = new ArrayList<>();
        com.google.gson.JsonParser parser = new com.google.gson.JsonParser();
        JsonArray array = parser.parse(str).getAsJsonArray();
        for (JsonElement element : array) {
            Gson gson = new Gson();
            PersonResult person = gson.fromJson(element, PersonResult.class);
            persons.add(person);
        }

        return persons;
    }

    public static VersionInfo getVersionResult(String str) {
        VersionInfo result = new VersionInfo();
        com.google.gson.JsonParser parser = new com.google.gson.JsonParser();
        JsonArray array = parser.parse(str).getAsJsonArray();
        for (JsonElement element : array) {
            Gson gson = new Gson();
            result = gson.fromJson(element, VersionInfo.class);
        }

        return result;
    }
}
