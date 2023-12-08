package edu.brown.cs.student.main.server.responses;

import com.squareup.moshi.Moshi;

import java.util.Map;

public record MeikDataResponse(String meikID, Map<String,Object> data, String message
                               ) {



    public String serialize() {
        Moshi moshi = new Moshi.Builder().build();
        return moshi.adapter(MeikDataResponse.class).toJson(this);
    }
}
