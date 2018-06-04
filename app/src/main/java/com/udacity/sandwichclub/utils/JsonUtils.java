package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) throws JSONException {


        JSONObject SWdata = new JSONObject(json);


        String sName = SWdata.getJSONObject("name").getString("mainName");

        JSONArray sNameAlias = SWdata.getJSONObject("name").getJSONArray("alsoKnownAs");
        ArrayList<String> alsoKnownAs = new ArrayList<>(sNameAlias.length());
        for (int i = 0; i < sNameAlias.length(); i++) {
            alsoKnownAs.add(sNameAlias.getString(i));
        }

        String sPlaceOfOrigin = SWdata.get("placeOfOrigin").toString();

        String sDescription = SWdata.getString("description");

        String sImageUrl = SWdata.getString("image");

        JSONArray sIngredients = SWdata.getJSONArray("ingredients");

        ArrayList<String> sIngridientArray = new ArrayList<>(sIngredients.length());
        for (int i = 0; i < sIngredients.length(); i++) {
            sIngridientArray.add(sIngredients.getString(i));
        }
        Sandwich sandwich = new Sandwich(sName, alsoKnownAs, sPlaceOfOrigin, sDescription, sImageUrl, sIngridientArray);
        return sandwich;

    }
}
