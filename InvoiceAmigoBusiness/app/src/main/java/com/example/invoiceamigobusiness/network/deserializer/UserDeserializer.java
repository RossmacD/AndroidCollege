package com.example.invoiceamigobusiness.network.deserializer;

import android.util.Log;

import com.example.invoiceamigobusiness.network.model.User;
import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * Unwrap user response - response arrives as response.body.user.xxx when Gson converter expects response.body.xxx
 */
public class UserDeserializer implements JsonDeserializer<User> {
    @Override
    public User deserialize(JsonElement je, Type type, JsonDeserializationContext jdc)
            throws JsonParseException
    {
        Log.d("Ross","haaaaa ");
        // Get the "user" element from the parsed JSON
        JsonElement content=je.getAsJsonObject();
        if (je.getAsJsonObject().has("user")) {
            content = je.getAsJsonObject().get("user");
        }



        // Deserialize it. You use a new instance of Gson to avoid infinite recursion
        // to this deserializer
        return new Gson().fromJson(content, User.class);

    }
}
