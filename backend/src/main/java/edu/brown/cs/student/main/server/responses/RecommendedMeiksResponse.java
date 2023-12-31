package edu.brown.cs.student.main.server.responses;

import com.squareup.moshi.Moshi;

import java.util.Map;

public record RecommendedMeiksResponse(String uid, Map<String,Object> results,Map<String,String> images) {

    /***
     * Serialize into json format.
     * @return
     */

    public String serialize() {
        Moshi moshi = new Moshi.Builder().build();
        return moshi.adapter(RecommendedMeiksResponse.class).toJson(this);
    }
}
