package com.skyincity.qaa.util.common;

import com.google.gson.Gson;
import com.thoughtworks.selenium.SeleniumException;

import net.sf.json.JSON;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

/**
 * Simple helper for Gson 
 * 
 * 
 * 
 */
public class JsonUtil {
    public static final Gson gson = new Gson();

    /**
	 * conversion into JSONArray is not allowed.
     * @param object - could be converted into JSONObject or null. conversion into JSONArray is not allowed.
     * @return
     */
    public static JSONObject toJsonObject(Object object) {
    	String jsonString= gson.toJson(object);
    	JSON json = JSONSerializer.toJSON(jsonString);
    	if (json== null || json.isEmpty()) {
    		return null;
    	} else if (json.isArray()) {
    		throw new IllegalArgumentException("object could converted into JSONArray");
    	}
    	return (JSONObject)json;
    }

    public static <T> T fromJson(String json, Class<T> classOfT) {
        return gson.fromJson(json, classOfT);
    }

    public static <T> T fromJson(JSONObject json, Class<T> classOfT) {
        return gson.fromJson(json.toString(), classOfT);
    }

	/**
	 * Load String into JSONObject
	 * @param strFrom
	 * @return v
	 * @throws Exception
	 */
    static public JSONObject loadJSONObjectFromString(String strFrom) throws Exception{
		
		JSON json=JSONSerializer.toJSON(strFrom);
		if (! (json instanceof JSONObject)){
			new IllegalStateException("String is not valid JSONObject"+strFrom);
		}
		return (JSONObject)json;
	}
    
    
    //
//    public static String toJsonString(Object object) {
//        if (object == null) {
//            throw new SeleniumException("\"object\" is null");
//        }
//        return gson.toJson(object);
//    }
//    
//    public static JSONObject toJsonObject(Object object) {
//    	if (object == null) {
//            throw new SeleniumException("\"object\" is null");
//        }
//    	String jsonString= toJsonString(object); 
//    	return (JSONObject) JSONSerializer.toJSON( jsonString); 
//    }

}
