package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private final static String KEY_NAME = "name";
    private final static String KEY_MAIN_NAME = "mainName";
    private final static String KEY_ALSO_KNOWN_AS = "alsoKnownAs";
    private final static String KEY_PLACE_OF_ORIGIN = "placeOfOrigin";
    private final static String KEY_DESCRIPTION = "description";
    private final static String KEY_IMAGE_URL = "image";
    private final static String KEY_INGREDIENTS = "ingredients";

    public static Sandwich parseSandwichJson(String json) {

        JSONObject sandwichObject;
        JSONObject name;
        String mainName = null;
        String placeOfOrigin = null;
        List<String> alsoKnownAs = null;
        String description = null;
        String image = null;
        List<String> ingredients = null;
        JSONArray alsoKnownArray;
        JSONArray ingredientsArray;

        try {

            sandwichObject = new JSONObject(json);
            name = sandwichObject.getJSONObject(KEY_NAME);
            mainName = name.getString(KEY_MAIN_NAME);
            description = sandwichObject.getString(KEY_DESCRIPTION);
            image = sandwichObject.getString(KEY_IMAGE_URL);
            placeOfOrigin = sandwichObject.getString(KEY_PLACE_OF_ORIGIN);
            alsoKnownArray = name.getJSONArray(KEY_ALSO_KNOWN_AS);
            ingredientsArray = sandwichObject.getJSONArray(KEY_INGREDIENTS);
            alsoKnownAs = getStrings(alsoKnownArray);
            ingredients = getStrings(ingredientsArray);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return new Sandwich(mainName,alsoKnownAs,placeOfOrigin,description,image,ingredients);
    }

    public static List<String> getStrings(JSONArray jsonArray){
        List<String> strings = new ArrayList<>();
        for(int i = 0; i< jsonArray.length(); i++){
            try {
                strings.add(jsonArray.getString(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return strings;
    }
}