package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {
    private static final String TAG = "JsonUtils";

    public static Sandwich parseSandwichJson(String jsonString) {
        if (jsonString == null) {
            return null;
        }

        final String nameField = "name";
        final String mainNameField = "mainName";
        final String imageField = "image";
        final String alsoKnownAsField = "alsoKnownAs";
        final String placeOfOriginField = "placeOfOrigin";
        final String descriptionField = "description";
        final String ingredientsField = "ingredients";

        try{
            Sandwich sandwich = new Sandwich();

            JSONObject jsonObject = new JSONObject(jsonString);

            JSONObject nameObject = jsonObject.getJSONObject(nameField);
            sandwich.setMainName(nameObject.getString(mainNameField));

            sandwich.setImage(jsonObject.getString(imageField));

            JSONArray alsoKnownAsNamesArray = nameObject.getJSONArray(alsoKnownAsField);
            sandwich.setAlsoKnownAs(jsonToList(alsoKnownAsNamesArray));
            sandwich.setPlaceOfOrigin(jsonObject.getString(placeOfOriginField));
            sandwich.setDescription(jsonObject.getString(descriptionField));

            JSONArray ingredientsArray = jsonObject.getJSONArray(ingredientsField);
            sandwich.setIngredients(jsonToList(ingredientsArray));

            return sandwich;

        } catch (final JSONException e){
            Log.e(TAG, "JSON error: " + e);
            return null;
        }
    }

    static private List<String> jsonToList(JSONArray jsonArray) throws JSONException {
        List<String> result = new ArrayList<>();

        if ( jsonArray != null){
            for (int i=0; i< jsonArray.length(); i++){
                result.add(jsonArray.getString(i));
            }
        }
        return result;
    }
}