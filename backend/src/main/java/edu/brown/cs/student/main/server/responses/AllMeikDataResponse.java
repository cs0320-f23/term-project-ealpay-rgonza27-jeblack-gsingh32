package edu.brown.cs.student.main.server.responses;

import com.squareup.moshi.Moshi;
import edu.brown.cs.student.main.User.Meik;


import java.util.List;
import java.util.Map;


public record AllMeikDataResponse(String message, List<Map<String,Object>> meiksData) {


    public String serialize() {
        Moshi moshi = new Moshi.Builder().build();
        return moshi.adapter(AllMeikDataResponse.class).toJson(this);
    }


}
