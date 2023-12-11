package edu.brown.cs.student.main.server.responses;

import com.squareup.moshi.Moshi;
import edu.brown.cs.student.main.User.User;

import java.util.Map;

public record UserUpdateResponse(String uid, Map<String,Object> result,String status) {


    public String serialize(){
        Moshi moshi = new Moshi.Builder().build();
        return moshi.adapter(UserUpdateResponse.class).toJson(this);

    }
}
