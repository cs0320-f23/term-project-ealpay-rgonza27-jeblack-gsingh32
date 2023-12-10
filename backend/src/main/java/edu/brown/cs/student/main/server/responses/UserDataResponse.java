package edu.brown.cs.student.main.server.responses;

import com.squareup.moshi.Moshi;

import java.util.Map;

public record UserDataResponse(String userID, Map<String,Object> data) {



    public String serialize() {
        Moshi moshi = new Moshi.Builder().build();
        return moshi.adapter(UserDataResponse.class).toJson(this);
    }
}
