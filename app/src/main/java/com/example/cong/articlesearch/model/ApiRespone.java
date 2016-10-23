package com.example.cong.articlesearch.model;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Cong on 22/10/2016.
 */

public class ApiRespone {
    @SerializedName("response")
    private JsonObject response;


    public JsonObject getResponse() {
        if(response==null)
            return new JsonObject();
        return response;
    }


}
